#!/bin/bash

# イメージのビルド
docker build -t java-training .

# コンテナの中に入る
docker run -it --rm -v "$(pwd)":/work -w /work java-training