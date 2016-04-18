## MVP架构模式

### 三层架构模式

三层架构是一个分层式的软件体系架构设计，它可适用于任何一个项目。通常意义上的三层架构就是将整个业务应用划分为：界面层（User Interface Layer），业务逻辑层（Business Logic Layer），数据访问层（Data access Layer）。

三层是为了解决整个应用程序中各个业务操作过程中不同阶段的代码封装的问题。为了使程序员更加专注的处理某阶段的业务逻辑；并且三层只是多层架构中的一种情况，完全可以根据需要分为多层.

### MVP 模式

- M model层
	- 从网络，数据库，文件，传感器，第三方等数据源读写数据。
	- 对外部的数据类型进行解析转换为APP内部数据交由上层处理。
	- 对数据的临时存储，管理，协调上层数据请求。

- V view层
	- 提供UI交互。
	- 在presenter的控制下修改UI。
	- 将业务事件交由presenter处理

- P presenter层
	- 从视图中获取数据，并获取相应的交互事件。
	- 将数据交由逻辑层处理。
	- 从逻辑层获取的返回的数据更新UI

### 例子

#### 登录

对于常见的登录操作，无非是界面上输入账号密码，登录等。

那么我们会定义如下类

- UserBean ： 用户的实体类，定义用户名，密码等。

- IUserModel: 用户的逻辑处理接口
	- 获取用户的保存的用户名，密码等（从数据库，或者网络等）
	- 验证用户登录的用户名和密码是否正确，并返回结果。

- UserModel ： 实现用户的逻辑处理接口

- IUserView : 用户尸体的接口类
	- 获取用户名。
	- 获取密码。
	- 显示结果
- Activity： 实现IUserView接口

- UserPresenter ： 沟通Model 和View
	- login方法。

#### 获取天气并显示在界面上

根据如上逻辑，我们可分为如下定义

- WeatherInfo： 天气的基本信息，java bean 类。
- IWeatherView: 天气的显示类。包含如下方法。
```java 
public interface WeatherView {
    void showLoading();
    void hideLoading();
    void showError();
    void setWeatherInfo(Weather weather);
}
``` 

- WeatherPresenter： 
	- 获取天气的逻辑（`void getWeather(String cityId);`）

- IWeatherModer:
	- 从网络上获取天气。（` void loadWeather(String cityId, OnWeatherListener listener);`）

### 将Activity作为Presenter

对于我们的Activity，我们通常在里面需要处理各种逻辑以及显示UI。那么如果按照下面这种方式Activity知识作为了presenter，而View将被从Activity中剥离出。

- Vu ：View层的父类，主要包含了加载视图的方法。

```java 
public interface Vu {

	//初始化视图
    void init(LayoutInflater inflater, ViewGroup container);

	//获取视图
    View getView();

}
```

- BasePresenterActivity ，Activty的父类。

```java 
public abstract  class BasePresenterActivity<T extends Vu> extends Activity {

    //View 视图接口，保持对View层的控制
    protected T vu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //获取View层的实例
            vu = getVuClass().newInstance();
            
            //初始化View视图
            vu.init(getLayoutInflater(),null);

            //获取View视图并设置为当前显示布局
            setContentView(vu.getView());
            
            //回调，
            onBindView();


        }  catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        //销毁
        onVuDestroy();

        super.onDestroy();
        vu = null;

    }

    protected  void onVuDestroy(){}

    protected  void onBindView(){}

    public abstract Class<T> getVuClass();
}


```

对于activity和Vu已经实现，下面看基于上面的例子。

对于View层，我们需要实现Vu
```java 
public class HelloVu implements Vu {

    private View view;
    private TextView tv_hello;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {

        //初始化视图
        view = inflater.inflate(R.layout.activity_main,container,false);

        //查找控件
        tv_hello = ((TextView) view.findViewById(R.id.tv_hello));

    }

    @Override
    public View getView() {
        return view;
    }


    //View层的方法，更新UI
    public void setMessage(String msg){
        tv_hello.setText(msg);
    }


}

```
- Activity 层的实现，充当着presenter

```java 
/**
 * Created by Alex_MaHao on 2016/4/18.
 */
public class HelloActivity extends BasePresenterActivity<HelloVu> {

    @Override
    protected void onBindView() {

        vu.setMessage("ss");

    }


    @Override
    protected void onVuDestroy() {
        super.onVuDestroy();
    }

    @Override
    public Class<HelloVu> getVuClass() {
        return HelloVu.class;
    }
}


```



