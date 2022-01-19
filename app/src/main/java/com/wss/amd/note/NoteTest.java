package com.wss.amd.note;

import com.wss.amd.note.designpattern.adapter.classs.Dc5V;
import com.wss.amd.note.designpattern.adapter.classs.Dc5VAdapter;
import com.wss.amd.note.designpattern.adapter.obj.SixiDumplings;
import com.wss.amd.note.designpattern.adapter.obj.SteamedBunsAdapter;
import com.wss.amd.note.designpattern.bridge.Circle;
import com.wss.amd.note.designpattern.bridge.Rectangle;
import com.wss.amd.note.designpattern.bridge.Shape;
import com.wss.amd.note.designpattern.bridge.pen.GreenPen;
import com.wss.amd.note.designpattern.bridge.pen.RedPen;
import com.wss.amd.note.designpattern.builder.TestDialog;
import com.wss.amd.note.designpattern.combination.Employee;
import com.wss.amd.note.designpattern.command.WaiterInvoker;
import com.wss.amd.note.designpattern.command.command.NoodleCommand;
import com.wss.amd.note.designpattern.command.command.RiceCommand;
import com.wss.amd.note.designpattern.command.command.StirFryCommand;
import com.wss.amd.note.designpattern.decorator.Egg;
import com.wss.amd.note.designpattern.decorator.GreenPepperShreddedMeat;
import com.wss.amd.note.designpattern.decorator.PotatoesBurnFireSirloin;
import com.wss.amd.note.designpattern.decorator.Vegetables;
import com.wss.amd.note.designpattern.decorator.base.Food;
import com.wss.amd.note.designpattern.decorator.base.Noodle;
import com.wss.amd.note.designpattern.decorator.base.Rice;
import com.wss.amd.note.designpattern.facade.FoodMaker;
import com.wss.amd.note.designpattern.fracory.abstractfactory.AssembleComputer;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IComputerFactory;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.computer.HaweiComputerFactoryFactory;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.computer.IntelComputerFactoryFactory;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.computer.MacComputerFactoryFactory;
import com.wss.amd.note.designpattern.fracory.normalfactory.ConfigFactory;
import com.wss.amd.note.designpattern.fracory.simplefactory.SimpleFactory;
import com.wss.amd.note.designpattern.fracory.simplefactory.config.IConfig;
import com.wss.amd.note.designpattern.interpreter.GameEnvironment;
import com.wss.amd.note.designpattern.interpreter.GameUser;
import com.wss.amd.note.designpattern.iterator.Iterator;
import com.wss.amd.note.designpattern.iterator.UserAggregate;
import com.wss.amd.note.designpattern.mediator.Colleague;
import com.wss.amd.note.designpattern.mediator.ConcreteColleague1;
import com.wss.amd.note.designpattern.mediator.ConcreteColleague2;
import com.wss.amd.note.designpattern.mediator.ConcreteMediator;
import com.wss.amd.note.designpattern.mediator.Mediator;
import com.wss.amd.note.designpattern.memorandum.Caretaker;
import com.wss.amd.note.designpattern.memorandum.Originator;
import com.wss.amd.note.designpattern.observer.BinaryObserver;
import com.wss.amd.note.designpattern.observer.HexaObserver;
import com.wss.amd.note.designpattern.observer.Subject;
import com.wss.amd.note.designpattern.proxy.User;
import com.wss.amd.note.designpattern.proxy.UserService;
import com.wss.amd.note.designpattern.proxy.UserServiceProxy;
import com.wss.amd.note.designpattern.responsibility.LocationRuleHandler;
import com.wss.amd.note.designpattern.responsibility.NewUserRuleHandler;
import com.wss.amd.note.designpattern.responsibility.ParticipantsRuleHandler;
import com.wss.amd.note.designpattern.responsibility.People;
import com.wss.amd.note.designpattern.state.InvisibleState;
import com.wss.amd.note.designpattern.state.OnLineState;
import com.wss.amd.note.designpattern.state.QQUser;
import com.wss.amd.note.designpattern.strategy.NoodleStrategy;
import com.wss.amd.note.designpattern.strategy.NoonEatWhat;
import com.wss.amd.note.designpattern.strategy.RiceStrategy;
import com.wss.amd.note.designpattern.template.AbsTemplateMethod;
import com.wss.amd.note.designpattern.template.TemplateMethod;
import com.wss.amd.note.designpattern.visitor.SetMaterial;
import com.wss.amd.note.designpattern.visitor.company.ArtCompany;
import com.wss.amd.note.designpattern.visitor.company.MintCompany;
import com.wss.amd.note.designpattern.visitor.material.Copper;
import com.wss.amd.note.designpattern.visitor.material.Paper;
import com.wss.common.base.BaseApplication;

import org.junit.Test;

/**
 * Describe：测试类
 * Created by 吴天强 on 2022/1/17.
 */
public class NoteTest {

    @Test
    public void testSimpleFactory() {

        //创建开发环境
        IConfig devConfig = SimpleFactory.createConfig(1);

        //创建生产环境
        IConfig proConfig = SimpleFactory.createConfig(0);

    }

    @Test
    public void testConfigFactory() {
        //普通工厂
        ConfigFactory configFactory = new ConfigFactory();
        IConfig config = configFactory.createConfig();
        String serviceUrl = config.getServiceUrl();
    }

    @Test
    public void testAbstractFactory() {
        //抽象工厂
        IComputerFactory factory;

        //创建华为工厂、组装华为电脑
        factory = new HaweiComputerFactoryFactory();
        AssembleComputer huaweiComputer = new AssembleComputer(factory.createCPU(), factory.createHardDisk()
                , factory.createMainBoard());

        //创建Intel工厂、组装Intel电脑
        factory = new IntelComputerFactoryFactory();
        AssembleComputer intelComputer = new AssembleComputer(factory.createCPU(), factory.createHardDisk()
                , factory.createMainBoard());

        //创建苹果工厂、组装苹果电脑
        factory = new MacComputerFactoryFactory();
        AssembleComputer macComputer = new AssembleComputer(factory.createCPU(), factory.createHardDisk()
                , factory.createMainBoard());

    }

    @Test
    public void testBuilderDialog() {
        //建造者
        new TestDialog.Builder(BaseApplication.i())
                .setContent("对话框内容")
                .setCanCancel(false)
                .build()
                .show();
    }

    @Test
    public void testUserProxy() {
        //代理模式
        UserService service = new UserServiceProxy();
        User admin = service.creteAdmin();
        User user = service.createUser();
    }

    @Test
    public void testObjAdapter() {
        //测试 对象适配器
        SixiDumplings sixiDumplings = new SixiDumplings();
        SteamedBunsAdapter bunsAdapter = new SteamedBunsAdapter(sixiDumplings);
        bunsAdapter.bunsFilling();
        bunsAdapter.steam();
    }

    @Test
    public void testClassAdapter() {
        //测试类适配器
        Dc5V dc5V = new Dc5VAdapter();
        int i = dc5V.dc5v();
    }

    @Test
    public void testBridge() {
        //桥梁模式

        //红色的圆圈
        Shape circle = new Circle(10, new RedPen());
        circle.draw();

        //绿色的矩形
        Shape rectangle = new Rectangle(10, 10, new GreenPen());
        rectangle.draw();
    }

    @Test
    public void testFoodMaker() {
        //门面模式
        FoodMaker foodMaker = new FoodMaker();
        foodMaker.makeBreakfast();
        foodMaker.makeLunch();
        foodMaker.makeDinner();
    }


    @Test
    public void testDecorator() {
        //餐馆来了一位小哥， 他对老板说
        //老板，来米饭
        Food rice = new Rice();
        System.out.println(rice.getName() + "，价格：" + rice.getPrice() + "元");
        //加一分土豆烧牛腩
        rice = new PotatoesBurnFireSirloin(rice);
        System.out.println(rice.getName() + "，价格：" + rice.getPrice() + "元");
        //再加个鸡蛋
        rice = new Egg(rice);
        System.out.println(rice.getName() + "，价格：" + rice.getPrice() + "元");
        System.out.println();

        //餐馆又来了一位大哥，他饭量比较好，需要双份的土豆牛腩和双份的青椒肉丝以及青菜、一个鸡蛋
        //老板，来双份面条
        Food noodle = new Noodle();
        System.out.println(noodle.getName() + "，价格：" + noodle.getPrice() + "元");
        //加双份土豆牛腩
        noodle = new PotatoesBurnFireSirloin(new PotatoesBurnFireSirloin(noodle));
        System.out.println(noodle.getName() + "，价格：" + noodle.getPrice() + "元");
        //再加双份的青椒肉丝
        noodle = new GreenPepperShreddedMeat(new GreenPepperShreddedMeat(noodle));
        System.out.println(noodle.getName() + "，价格：" + noodle.getPrice() + "元");
        //再来青菜
        noodle = new Vegetables(noodle);
        System.out.println(noodle.getName() + "，价格：" + noodle.getPrice() + "元");
        //再来一个鸡蛋
        noodle = new Egg(noodle);
        System.out.println(noodle.getName() + "，价格：" + noodle.getPrice() + "元");

    }

    @Test
    public void testCombination() {

        Employee employee = new Employee("张三", "信息部");
        employee.add(new Employee("李四", "信息部"));
        employee.add(new Employee("王五", "信息部"));

        System.out.println(employee.toString());
    }

    @Test
    public void testStrategy() {
        //10个人去吃米饭
        NoonEatWhat noonEatWhat = new NoonEatWhat(new RiceStrategy());
        noonEatWhat.executeEat(10);

        //5个人去吃面条
        NoonEatWhat noonEatWhat1 = new NoonEatWhat(new NoodleStrategy());
        noonEatWhat1.executeEat(5);
    }


    @Test
    public void testObserver() {

        //定义主题（被观察者）
        Subject subject = new Subject();

        //定义两个观察者
        new BinaryObserver(subject);
        new HexaObserver(subject);

        //模拟数据变更，被观察者有了变化
        subject.setState(22);
    }


    @Test
    public void testResponsibility() {

        People people = new People();
        people.setNewUser(true);
        people.setLocation("上海");
        people.setParticipants(222);

        NewUserRuleHandler newUserRuleHandler = new NewUserRuleHandler();
        LocationRuleHandler locationRuleHandler = new LocationRuleHandler();
        ParticipantsRuleHandler participantsRuleHandler = new ParticipantsRuleHandler();

        locationRuleHandler.setSuccessor(newUserRuleHandler);
        participantsRuleHandler.setSuccessor(locationRuleHandler);
        newUserRuleHandler.apply(people);
    }

    @Test
    public void testAbsTemplateMethod() {
        AbsTemplateMethod templateMethod = new TemplateMethod();
        templateMethod.templateMethod();
    }

    @Test
    public void testState() {
        QQUser qqUser = new QQUser("人生如梦");

        //隐身
        InvisibleState invisibleState = new InvisibleState();
        invisibleState.doAction(qqUser);
        System.out.println(qqUser.getName() + "当前状态：" + qqUser.getState().toString());

        //上线
        OnLineState onLineState = new OnLineState();
        onLineState.doAction(qqUser);
        System.out.println(qqUser.getName() + "当前状态：" + qqUser.getState().toString());
    }

    @Test
    public void testIterator() {
        UserAggregate<User> aggregate = new UserAggregate<>();
        aggregate.add(new User("张三"));
        aggregate.add(new User("李四"));
        aggregate.add(new User("王五"));

        Iterator iterator = aggregate.iterator();
        System.out.println("First：" + iterator.first());
        System.out.println("Has next：" + iterator.hasNext());
        System.out.println("Next：" + iterator.next());
        System.out.println("Next：" + iterator.next());
        System.out.println("Has next：" + iterator.hasNext());

    }

    @Test
    public void testCommand() {
        //饭店对外生成了一个服务员,同时也生成了米饭、面条、炒菜三个菜单
        WaiterInvoker waiterInvoker = new WaiterInvoker(new RiceCommand(), new NoodleCommand(), new StirFryCommand());
        //饭店开张，来了一位顾客，选择了米饭
        waiterInvoker.chooseRice();
        //又继续点了一份面条
        waiterInvoker.chooseNoodle();
        //最后又加了一份炒菜
        waiterInvoker.chooseStirFry();
    }

    @Test
    public void testMemorandum() {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        originator.setState("create");
        System.out.println("当前状态：" + originator.getState());
        //保存当前状态到备忘录
        caretaker.setMemento(originator.createMemento());
        originator.setState("working");
        System.out.println("当前状态：" + originator.getState());
        originator.restoreMemento(caretaker.getMemento());
        System.out.println("当前状态：" + originator.getState());
    }

    @Test
    public void testVisitor() {

        //定义材料集
        SetMaterial setMaterial = new SetMaterial();
        //添加纸
        setMaterial.add(new Paper());
        //添加铜
        setMaterial.add(new Copper());

        //艺术公司制作工艺品
        String artSet = setMaterial.accept(new ArtCompany());
        System.out.println("艺术公司成果：" + artSet);
        //造币公司制作钞票
        String mintSet = setMaterial.accept(new MintCompany());
        System.out.println("造币公司成果：" + mintSet);

    }

    @Test
    public void testMediator() {
        Mediator mediator = new ConcreteMediator();
        Colleague concreteColleague1 = new ConcreteColleague1();
        Colleague concreteColleague2 = new ConcreteColleague2();
        mediator.register(concreteColleague1);
        mediator.register(concreteColleague2);
        concreteColleague1.send();
        concreteColleague2.send();
    }

    @Test
    public void testInterpreter() {
        //假设：阵营是齐国、吴国且等级大于50的玩家只能参加普通副本《捉泥鳅》，
        //其他的则可以参加高级副本《鹊桥仙》
        //定义用户环境
        GameEnvironment gameEnvironment = new GameEnvironment();
        //第一位 来自齐国的66级的李逍遥
        gameEnvironment.operation(new GameUser("李逍遥", 66, "齐国"));
        //第二位 来自楚国的51级的张小凡
        gameEnvironment.operation(new GameUser("张小凡", 51, "楚国"));
        //第三位 来自燕国的11级的高渐离
        gameEnvironment.operation(new GameUser("高渐离", 11, "燕国"));

    }

}
