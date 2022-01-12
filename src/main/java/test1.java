import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Project name(项目名称)：sql注入
 * Package(包名): PACKAGE_NAME
 * Class(类名): test1
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/1/12
 * Time(创建时间)： 21:10
 * Version(版本): 1.0
 * Description(描述)： 演示sql注入现象
 */

public class test1
{
    public static void main(String[] args)
    {
        Map<String, String> userLoginInfo = initUI();
        System.out.println(login(userLoginInfo) ? "登录成功" : "登录失败");
    }

    //传入一个map集合，并拿它与数据进行对比，如果账户密码合法就返回“true”，否则返回“false”
    private static boolean login(Map<String, String> userLoginInfo)
    {
        boolean flag = false;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            //1、注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
            //3、获取数据库操作对象
            stmt = conn.createStatement();
            //4、执行sql语句
            //String sql = "select .* from t_user where userName = '" + userLoginInfo.get("userName") + "' and userPassword = '" + userLoginInfo.get("' userPassword;");
            String sql = "select * from t_user where loginName = '" + userLoginInfo.get("userName") + "' and loginPwd = '" + userLoginInfo.get("userPassword") + "'";
            //lll ' or '1'='1
            rs = stmt.executeQuery(sql);
            flag = rs.next();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                }
            }
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                }
            }
        }
        return flag;
    }

    //输入函数，用户在此函数中输入自己的账户和密码，并存入map集合中去与数据库中的数据进行比较
    private static Map<String, String> initUI()
    {
        Scanner s = new Scanner(System.in);

        System.out.printf("请输入账号： ");
        String userName = s.nextLine();
        System.out.printf("请输入密码： ");
        String userPassword = s.nextLine();

        Map<String, String> userLoginInfo = new HashMap<>();
        userLoginInfo.put("userName", userName);
        userLoginInfo.put("userPassword", userPassword);

        return userLoginInfo;
    }
}
