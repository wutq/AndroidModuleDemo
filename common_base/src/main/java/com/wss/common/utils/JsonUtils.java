package com.wss.common.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Describe：Gson 解析封装
 * Created by 吴天强 on 2018/12/12.
 */
public class JsonUtils {

    /**
     * 对象转Json
     *
     * @param obj 对象
     * @return json  String
     */
    public static String toJson(Object obj) {
        if (ValidUtils.isValid(obj)) {
            return new Gson().toJson(obj);
        }
        return "{}";
    }

    /**
     * 解析T对象
     *
     * @param json json
     * @param type 目标类型
     * @param <T>  类型
     * @return T
     */
    public static <T> T getObject(String json, Class<T> type) {
        return new Gson().fromJson(json, type);
    }

    /**
     * 解析String
     *
     * @param json json
     * @param key  key
     * @return String
     */
    @Contract("null, _ -> !null")
    public static String getString(JsonObject json, String key) {
        if (json == null) {
            return "";
        }
        JsonElement jsonElement = json.get(key);
        if (jsonElement == null) {
            return "";
        }
        return jsonElement.getAsString();
    }

    /**
     * 解析String
     *
     * @param element json
     * @return String
     */
    @Contract("null, _ -> !null")
    public static String getString(JsonElement element, String key) {
        if (element == null) {
            return "";
        }
        return getString(element.getAsJsonObject(), key);
    }

    /**
     * 解析String
     *
     * @param json json
     * @param key  key
     * @return String
     */
    public static String getString(String json, String key) {
        if (!isJsonObject(json)) {
            return "";
        }
        JsonElement jsonElement = getJsonObject(json).get(key);
        if (jsonElement == null) {
            return "";
        }
        //判断json对象是否为Object
        if (jsonElement.isJsonObject()) {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return asJsonObject.toString();
        }
        //判断json对象是否为数组
        if (jsonElement.isJsonArray()) {
            JsonArray asJsonArray = jsonElement.getAsJsonArray();
            return asJsonArray.toString();
        }
        return jsonElement.getAsString();
    }


    /**
     * 解析boolean
     *
     * @param json json
     * @param key  key
     * @return boolean
     */
    @Contract("null, _ -> false")
    public static boolean getBoolean(String json, String key) {
        if (json == null) {
            return false;
        }
        JsonElement jsonElement = getJsonObject(json).get(key);
        return jsonElement != null && jsonElement.getAsBoolean();
    }

    /**
     * 解析int
     *
     * @param json json
     * @param key  key
     * @return String
     */
    public static int getInt(String json, String key) {
        JsonElement jsonElement = getJsonObject(json).get(key);
        if (jsonElement == null) {
            return 0;
        }
        return jsonElement.getAsInt();
    }

    /**
     * 解析int
     *
     * @param json json
     * @param key  key
     * @return boolean
     */
    public static int getInt(JsonObject json, String key) {
        if (json == null) {
            return 0;
        }
        JsonElement jsonElement = json.get(key);
        if (jsonElement == null) {
            return 0;
        }
        return jsonElement.getAsInt();
    }


    /**
     * 解析JsonObject
     *
     * @param json json
     * @return JsonArray
     */
    public static JsonObject getJsonObject(String json) {
        if (TextUtils.isEmpty(json)) {
            return new JsonObject();
        }
        return new JsonParser().parse(json).getAsJsonObject();
    }

    /**
     * 解析JsonArray
     *
     * @param json json
     * @param key  key
     * @return JsonArray
     */
    @Contract("null, _ -> new")
    public static JsonArray getJsonArray(JsonObject json, String key) {
        if (json == null) {
            return new JsonArray();
        }
        JsonElement jsonElement = json.get(key);
        if (jsonElement == null) {
            return new JsonArray();
        }
        return jsonElement.getAsJsonArray();
    }


    /**
     * 解析Map<String, String>
     *
     * @param json json
     * @return List
     */
    @NotNull
    public static LinkedHashMap<String, String> getMap(@NotNull String json) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            result.putAll(new Gson().fromJson(jsonObject, new TypeToken<LinkedHashMap<String, String>>() {
            }.getType()));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析List
     *
     * @param json  json
     * @param clazz clazz
     * @return List<T>
     */
    public static <T> List<T> getList(String json, Class<T> clazz) {
        if (!ValidUtils.isValid(json)) {
            return new ArrayList<>();
        }
        List<T> list = null;
        try {
            list = new ArrayList<>();
            Type type = new TypeToken<ArrayList<JsonObject>>() {
            }.getType();
            List<JsonObject> jsonObjects = new Gson().fromJson(json, type);
            if (ValidUtils.isValid(jsonObjects)) {
                for (JsonObject jsonObject : jsonObjects) {
                    list.add(new Gson().fromJson(jsonObject, clazz));
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析List
     *
     * @param json json
     * @return List<String>
     */
    public static List<String> getList(String json) {
        if (!isJsonArray(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }

    /**
     * 判断是否JSON数组
     *
     * @param json json
     * @return boolean
     */
    public static boolean isJsonArray(String json) {
        JsonElement jsonElement = getJsonElement(json);
        if (jsonElement == null) {
            return false;
        }
        return jsonElement.isJsonArray();
    }

    /**
     * 判断是否JSON对象
     *
     * @param json json
     * @return boolean
     */
    public static boolean isJsonObject(String json) {
        JsonElement jsonElement = getJsonElement(json);
        if (jsonElement == null) {
            return false;
        }
        return jsonElement.isJsonObject();
    }

    /**
     * 获取JsonElement
     *
     * @param json json
     * @return JsonElement
     */
    @Nullable
    private static JsonElement getJsonElement(String json) {
        JsonElement jsonElement;
        if (!ValidUtils.isValid(json)) {
            return null;
        }
        try {
            jsonElement = new JsonParser().parse(json);
        } catch (Exception e) {
            return null;
        }
        return jsonElement;
    }
}
