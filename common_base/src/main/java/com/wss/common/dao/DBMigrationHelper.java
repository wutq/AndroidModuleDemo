package com.wss.common.dao;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Describe：数据库升级操作类
 * Created by 吴天强 on 2018/11/5.
 */
public class DBMigrationHelper {

    private static final String SQLITE_MASTER = "sqlite_master";
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";

    static void migrate(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        Logger.e("【The Old Database Version】" + db.getVersion());
        Database database = new StandardDatabase(db);
        migrate(database, daoClasses);
    }


    @SafeVarargs
    static void migrate(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        Logger.e("【Generate temp table】start");
        generateTempTables(database, daoClasses);
        Logger.e("【Generate temp table】complete");

        Logger.e("【Drop all table and recreate all table】");
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(database, daoClass);
            dropTable(database, true, daoConfig);
            createTable(database, false, daoConfig);
        }

        Logger.e("【Restore data】start");
        restoreData(database, daoClasses);
        Logger.e("【Restore data】complete");
    }

    private static void dropTable(Database database, boolean ifExists, DaoConfig daoConfig) {
        String sql = String.format("DROP TABLE %s\"%s\"", ifExists ? "IF EXISTS " : "", daoConfig.tablename);
        database.execSQL(sql);
    }

    @SafeVarargs
    private static void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            String tempTableName = null;

            DaoConfig daoConfig = new DaoConfig(db, daoClass);
            String tableName = daoConfig.tablename;
            if (!isTableExists(db, false, tableName)) {
                Logger.e("【New Table】" + tableName);
                continue;
            }
            try {
                tempTableName = daoConfig.tablename.concat("_TEMP");
                StringBuilder dropTableStringBuilder = new StringBuilder();
                dropTableStringBuilder.append("DROP TABLE IF EXISTS ").append(tempTableName).append(";");
                Logger.e("【Generate temp table】 dropTableStringBuilder:" + dropTableStringBuilder);
                db.execSQL(dropTableStringBuilder.toString());

                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("CREATE TEMPORARY TABLE ").append(tempTableName);
                insertTableStringBuilder.append(" AS SELECT * FROM ").append(tableName).append(";");
                Logger.e("【Generate temp table】 insertTableStringBuilder:" + insertTableStringBuilder);
                db.execSQL(insertTableStringBuilder.toString());
                Logger.e("【Table】" + tableName + "\n ---Columns-->" + getColumnsStr(daoConfig));
                Logger.e("【Generate temp table】" + tempTableName);
            } catch (SQLException e) {
                Logger.e("【Failed to generate temp table】" + tempTableName, e);
            }
        }
    }

    @Contract("null, _, _ -> false")
    private static boolean isTableExists(Database db, boolean isTemp, String tableName) {
        if (db == null || TextUtils.isEmpty(tableName)) {
            return false;
        }
        String dbName = isTemp ? SQLITE_TEMP_MASTER : SQLITE_MASTER;
        String sql = "SELECT COUNT(*) FROM " + dbName + " WHERE type = ? AND name = ?";
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = db.rawQuery(sql, new String[]{"table", tableName});
            if (cursor == null || !cursor.moveToFirst()) {
                return false;
            }
            count = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count > 0;
    }


    private static String getColumnsStr(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "no columns";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < daoConfig.allColumns.length; i++) {
            builder.append(daoConfig.allColumns[i]);
            builder.append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


    @SafeVarargs
    private static void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, daoClass);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            isTableExists(db, true, tempTableName);
            try {
                // get all columns from tempTable, take careful to use the columns list
                List<String> columns = getColumns(db, tempTableName);
                ArrayList<String> properties = new ArrayList<>(columns.size());
                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;
                    if (columns.contains(columnName)) {
                        properties.add(columnName);
                    }
                }
                if (properties.size() > 0) {
                    final String columnSQL = TextUtils.join(",", properties);

                    StringBuilder insertTableStringBuilder = new StringBuilder();
                    insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
                    insertTableStringBuilder.append(columnSQL);
                    insertTableStringBuilder.append(") SELECT ");
                    insertTableStringBuilder.append(columnSQL);
                    insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                    Logger.e("【Restore data】 db sql: " + insertTableStringBuilder);
                    db.execSQL(insertTableStringBuilder.toString());
                    Logger.e("【Restore data】 to " + tableName);
                }
                StringBuilder dropTableStringBuilder = new StringBuilder();
                dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
                db.execSQL(dropTableStringBuilder.toString());
                Logger.e("【Drop temp table】" + tempTableName);
            } catch (SQLException e) {
                Logger.e("【Failed to restore data from temp table 】" + tempTableName, e);
            }
        }
    }

    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = null;
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null)) {
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == columns) {
                columns = new ArrayList<>();
            }
        }
        return columns;
    }

    private static void createTable(Database db, boolean ifNotExists, DaoConfig daoConfig) {
        String tableName = daoConfig.tablename;
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(ifNotExists ? "IF NOT EXISTS " : "");
        builder.append(tableName);
        builder.append(getColumnsSql(daoConfig));
        Logger.e("【createTable】 sql:" + builder.toString());
        db.execSQL(builder.toString()); // 6: Description
    }

    private static String getColumnsSql(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder(" (");
        for (int i = 0; i < daoConfig.properties.length; i++) {
            builder.append(String.format("\"%s\" %s,", daoConfig.properties[i].columnName,
                    getPropertyType(daoConfig.properties[i].type)));
        }
        if (daoConfig.properties.length > 0 && builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("); ");
        return builder.toString();
    }

    /**
     * 根据字段类型返回对应的数据库字段语句
     *
     * @param type
     * @return
     */
    private static String getPropertyType(Class<?> type) {
        if (type.equals(byte[].class)) {
            return "BLOB";
        } else if (type.equals(String.class)) {
            return "TEXT DEFAULT ''";
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)
                || type.equals(int.class) || type.equals(Integer.class)
                || type.equals(long.class) || type.equals(Long.class)
                || type.equals(Date.class) || type.equals(Byte.class)) {
            return "INTEGER DEFAULT (0)";
        } else if (type.equals(float.class) || type.equals(Float.class)
                || type.equals(double.class) || type.equals(Double.class)) {
            return "REAL DEFAULT (0)";
        }
        return "TEXT DEFAULT ''";
    }
}
