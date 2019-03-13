configuration of tomcat db pool
add to tomcat config/server.xml this datasource

<GlobalNamingResources>
    <Resource auth="Container" defaultAutoCommit="true" description="MySQL database." 
    driverClassName="com.mysql.jdbc.Driver" 
    factory="org.apache.tomcat.jdbc.pool.DataSourceFactory" 
    global="jdbc/university_selection_committee" maxIdle="10" maxTotal="10" 
    maxWaitMillis="10000" name="jdbc/university_selection_committee" password="root" 
    removeAbandonedTimeout="300" type="javax.sql.DataSource" 
    url="jdbc:mysql://localhost:3306/university_selection_committee" 
    username="root"/>
</GlobalNamingResources>

add to META-INF context.xml
<Context>
     
    <ResourceLink name="jdbc/university_selection_committee"
    global="jdbc/university_selection_committee"
    type="javax.sql.DataSource"
/>
    
and add support for ru language in tomcate
     
</Context>