import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Project name(项目名称)：sql注入
 * Package(包名): PACKAGE_NAME
 * Class(类名): test
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/1/12
 * Time(创建时间)： 20:57
 * Version(版本): 1.0
 * Description(描述)：
 */

public class test
{
    public static void main(String[] args)
    {
        // 初始化界面
        Map<String, String> userLoginInfo = initUI();
        // 验证用户名和密码
        boolean loginSuccess = login(userLoginInfo);
        // 输出最后结果
        System.out.println(loginSuccess ? "登录成功" : "登录失败");
    }

    /**
     * 用户登录
     *
     * @param userLoginInfo 用户登录信息
     * @return true表示登录成功，false表示登录失败
     */
    private static boolean login(Map<String, String> userLoginInfo)
    {
        boolean loginSuccess = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            // 1、注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
            // 3、获取预编译的数据库操作对象
            // sql语句的框架中，一个?，表示一个占位符，一个?将来接收一个值。注意：?不要用单引号括起来
            String sql = "select * from t_user where loginName = ? and loginPwd = ?";
            // 程序执行到此处，会发送sql语句框架给DBMS，DBMS对sql语句框架进行预编译。
            ps = conn.prepareStatement(sql);
            // 给占位符?传值，第一个?的下标是1，第二个?的下标是2（JDBC中下标都从1开始）
            ps.setString(1, userLoginInfo.get("userName"));
            ps.setString(2, userLoginInfo.get("userPassword"));
            // 4、执行sql语句
            rs = ps.executeQuery();
            // 5、处理结果集
            if (rs.next())
            {
                loginSuccess = true;
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        finally
        {
            // 6、释放资源
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
            if (ps != null)
            {
                try
                {
                    ps.close();
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
        return loginSuccess;
    }


    /**
     * 初试化界面
     *
     * @return 用户输入的用户名和密码等登录信息
     */
    private static Map<String, String> initUI()
    {
        Scanner s = new Scanner(System.in);

        System.out.print("请输入用户:");
        String userName = s.nextLine();
        System.out.print("请输入密码:");
        String userPassword = s.nextLine();

        Map<String, String> userLoginInfo = new HashMap<>();
        userLoginInfo.put("userName", userName);
        userLoginInfo.put("userPassword", userPassword);

        return userLoginInfo;
    }
}
