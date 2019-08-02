> EasyORM——An Easy ORM for Java

### 配置文件

配置文件命名为：easy.properties

#### 必填参数：

* url：数据库地址
* username：数据库用户名
* password：数据库密码
* driverClassName：JDBC驱动类名

* databaseProvider：数据库提供商，可选的值为`mysql`、`oracle`和`SqlServer`，不区分大小写

#### 可选参数：

* dataSource：数据库连接池类名

示例：

```java
url=jdbc:mysql://localhost:3306/db_blog
username=root
password=mysqladmin
driverClassName=com.mysql.jdbc.Driver
#dataSource=org.apache.common.dbcp2.BasicDataSource
databaseProvider=mysql
```

### DML操作

单个表的数据库操作，已由`com.hegongshan.easy.orm.core.DAO`类实现。

使用时，自定义类，继承`DAO<T>`类即可。

#### 插入数据

* public int insert(T t) throws SQLException

#### 删除数据

* public int delete(T t) throws SQLException

通过联合主键删除

* public int deleteById(Object ... ids) throws SQLException

#### 更新数据

* public int update(T t) throws SQLException

#### 查询数据

* 查询一条数据

public T queryById(Object ... ids) throws SQLException

* 查询多条数据

> public List<T> queryList(String sql,Object ... params) throws SQLException

* 带分页的查询

> public PageResult<T> queryByPage(Page page) throws SQLException

