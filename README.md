# Java学習環境 実行方法

## コンテナの起動手順

``` bash
# イメージのビルド
docker build -t java-training .

# コンテナを起動して中に入る
docker run -it --rm -v $(pwd):/work java-training
```

## コンテナの終了手順

``` bash
# コンテナの外に出る
exit

# コンテナの終了
docker stop java-training

# コンテナの削除
docker rm java-training
```

## ソースコードのコンパイル・実行方法

- 1ファイルの場合

``` bash
# コンパイルと実行を別々に行う方法
javac Main.java
java Main

# コンパイルと実行を一度に行う方法
java Main.java
```

- 複数ファイルが関連する場合
  - クラスパスを指定しないとコンパイルできません。

``` bash
# コンパイル
# -d out: .classファイルの出力先 ここではoutディレクトを指定
# *.java: 全てのクラスをまとめてコンパイル（依存関係があるため）
javac -d out src/your-package/*.java

# 実行
# -cp: クラスパスの指定
java -cp out your-package.Main
```