# TCPとUDPによるネットワークプログラミング

- TCP
  - コネクション型であり、まずクライアントとサーバ間で接続を確立する
    - このとき「3ウェイ・ハンドシェイク」と呼ばれる手続きを行う
  - その後、確立した接続によってデータの送受信を行い、送受信が終わると接続を切断するという手順を踏む
  - TCPをベースにしたプロトコルはHTTP, POP3, SMNPなど

- UDP
  - コネクションレス型であり、クライアントとサーバ間で接続を確立する、という手順を持たない
  - UDPをベースにしたプロトコルはDNS, 音声や動画のストリーミングなど

## ネットワーク通信用クラスライブラリの変遷
- Java SEにおけるネットワーク通信用のクラスは、基本的に`java.net`パッケージに所属
- Java 1.4で導入されたJava NIO（あるいはJava 7で導入されたJava NIO.2）により、ネットワーク通信で利用するクラス群が刷新されている
  - Java NIOでは、従来からの機能に加えて「ノンブロッキングI/O」を利用できるのが特徴
  - クラスは以下のパッケージに所属する
    - `java.nio`
    - `java.nio.channels`
    - `java.nio.charset`
- 基本的には後から導入されたJava NIOクラス群を利用推奨

## ソケットとチャネル
- Java 1.3までのクラス（ソケットの抽象化）
  - TCP
    - クライアントとの接続確立: `java.net.ServerSocket`
    - データの読み書き: `java.net.Socket`
  - UDP
    - データの読み書き: `java.net.DatagramSocket`
- Java NIO（チャネルの抽象化）
  - TCP
    - クライアントとの接続確立: `java.nio.channels.ServerSocketChannel`
    - データ読み書き: `java.nio.channels.SocketChannel`
  - UDP
    - データ読み書き: `java.nio.channels.DatagramChannel`

- `ソケット`
  - ネットワークにおける「送信元IPアドレス、送信元ポート番号、宛先IPアドレス、宛先ポート番号」の組み合わせのことを指す
- `チャネル`
  - ソケットの概念を包含し、対象をネットワーク以外にもファイルなど、入出力が可能なリソースに広げた概念

## 代表的なクラス
- `java.net.InetSocketAddress`
  - ソケットアドレス、すなわちIPアドレス（またはホスト名）とポート番号の組み合わせを表すためのクラス
    - Java NIOのAPIの引数に使われる
  - サーバでは、サーバを起動するときに自身のソケットアドレスを指定する
    - サーバは自身のIPアドレスを知っているため、`new InetSocketAddress(55555)`のようにポート番号のみで初期化
  - クライアントでは、サーバと通信するときに宛先のソケットアドレスを指定する
    - `new InetSocketAddress("localhost", 55555)`のように初期化
- `java.nio.ByteBuffer`
  - バイト配列によってバッファを表すためのクラス
    - 送受信するデータの一時格納領域として利用する

## `ByteBuffer`へのプリミティブがたのデータの書き込み、読み出し
- `allocate()`メソッドにサイズをバイト数で指定することで、新しい`ByteBuffer`を生成する
- `ByteBuffer`に対しては`putInt()`メソッドや`putLong()`メソッドでデータを追加する
- データ追加後に取り出すためには、`flip()`メソッドによって`ByteBuffer`をフリップする
  - フリップすると、`ByteBuffer`の現在位置以降の内容が切り捨てられ、取り出し開始位置が`0`に設定される
- データを取り出すときは`getInt()`メソッドや`getLong()`メソッドを使用する

``` java
  ByteBuffer buffer = ByteBuffer.allocate(100);
  // 追加
  buffer.putInt(10);
  buffer.putLong(1000L);
  // 取り出し
  buffer.flip();
  int val1 = buffer.getInt();
  long val2 = buffer.getLong();
```

## `ByteBuffer`への文字列の追加と取り出し
- 文字列の`ByteBuffer`への追加（エンコード）は、`Charset`クラスの`encode()`メソッドを呼び出す
- `CharSet`における代表的な文字コードは、`StardardCharsets`クラスの定数として定義されている
- `Charset`クラスの`encode()`メソッドに文字列を渡すと、バッファに文字列が追加される
- 文字列の取り出し（デコード）は、`Charset`クラスの`decode()`メソッドを呼び出す

``` java
  ByteBuffer buffer = ByteBuffer.allocate(100);
  // 追加
  buffer.put(StandardCharsets.UTF_8.encode("foo"));
  // 取り出し
  buffer.flip();
  String str = StandardCharsets.UTF_8.decode(buffer).toString();
```

## ノンブロッキングI/O
- TCPサーバにはシングルスレッドで動作しており、2か所のブロッキングポイントがある
  - 1. TCPクライアントとの接続処理中は、他のTCPクライアントからの接続要求は受け付けない
  - 2. TCPクライアントからの受信中(リクエストの読み込み中)は、他のTCPクライアントからの受信は受け付けない
- このような課題は、ノンブロッキングI/Oで解決可能
  - ブロッキングが発生しないため、シングルスレッドであっても、複数の処理を効率的に切り替えながら実行可能

- TCPサーバは2つのチャネル(`ServerSocketChannel`と`SocketChannel`)はデフォルトではブロッキングモード
  - `configureBlocking()`メソッドに`false`を渡すことで、ノンブロッキングモードに変更可能
  - `ServerSocketChannel`の`accept()`メソッド => 接続未確立の場合は空振りし、直ちに`null`が返る
  - `SocketChannel`の`read()`メソッド => 読み込み準備未完了の場合は空振りし、直ちに`null`が返る
  - 空振りすることなく、適切な結果を得るためにはセレクタという仕組みを利用する