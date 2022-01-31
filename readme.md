# SocketCore

基于Socket的简单服务端与客户端通讯模块  
核心：

* 一个Server，用来接收客户端数据。
* 一个Client，用来连接服务端。

## 使用

### Server

```java
    // 使用新的服务端配置新建服务端对象
    Server server=new Server(
            new ServerConfig()
                // 设定服务端监听端口
                .port(16508)
                // 设定处理器数量与每个处理器管理的客户端连接数
                .max(5).tied(5)
                // 设定客户端消息处理器
                .handler(handler));
    // 服务端初始化
    server.init();
    // 服务端开始接受客户端连接请求
    server.start();
```

*注：handler在后面有说明*

### Client

```java
    Client client=new Client(
        new ClientConfig()
            // 设定连接的服务端IP与端口
            .ip("127.0.0.1").port(16508)
            // 设定服务端消息处理器
            .handler((client,message)->System.out.println(message)));
    // 客户端连接
    client.connect();
    // 获取控制台输入监听
    Scanner scanner=new Scanner(System.in);
    while(client.isConnected()){
        // 将控制台输入发送给服务端
        client.sendMessage(scanner.nextLine());
    }
```

*注：handler在后面有说明*

## 添加

1. 添加Jitpack仓库源

> maven
> ```xml
> <repositories>
>    <repository>
>        <id>jitpack.io</id>
>        <url>https://jitpack.io</url>
>    </repository>
> </repositories>
> ```

> Gradle
> ```text
> allprojects {
>   repositories {
>       maven { url 'https://jitpack.io' }
>   }
> }
> ```

2. 添加依赖

> maven
> ```xml
>    <dependencies>
>        <dependency>
>            <groupId>com.github.Verlif</groupId>
>            <artifactId>socket-core</artifactId>
>            <version>0.2</version>
>        </dependency>
>    </dependencies>
> ```

> Gradle
> ```text
> dependencies {
>   implementation 'com.github.Verlif:socket-core:0.2'
> }
> ```

## 通讯信息处理

本模块的信息是通过字符串的方式进行传递。 服务端使用`SocketHandler`接口进行客户端数据处理。 客户端使用`SocketHandler`接口进行服务端消息处理。 两端在新建对象时，其中的配置类中请添加自实现的消息处理接口。

`SocketHandler`接口

```java
public interface SocketHandler {

    /**
     * 当服务器连接数到达最大值时的拒绝策略
     *
     * @param socket 被拒绝的连接
     */
    default void onRejected(Socket socket) throws IOException {
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.println("the server is overload!");
        ps.flush();
        ps.close();
        socket.close();
    }

    /**
     * 当接收到数据时回调
     *
     * @param client  客户端套接字
     * @param message 接收到的数据
     */
    void receive(ClientHolder.ClientHandler client, String message);
}
```

------

`ReceiveHandler`

```java
public interface ReceiveHandler {

    /**
     * 当接收到数据时回调
     *
     * @param client  客户端套接字
     * @param message 接收到的数据
     */
    void receive(Client client, String message);
}
```
