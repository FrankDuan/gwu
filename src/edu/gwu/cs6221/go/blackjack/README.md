# A Simple Blackjack Game

## Installation
```directly run in terminal:
$ apt install golang-go
$ go get github.com/codegangsta/negroni
$ go get github.com/gorilla/mux
$ go build -o blackjack *.go
```

## Environment Variable

The resources directory can be placed to other directory. Then you need
to set the WEBROOT Variable to the corresponding path.
```directly run in terminal:
export WEBROOT=path_to_blackjack_resources_file
```
Otherwise, you should not set the WEBROOT variable and need to unset it 
if necessary.
```directly run in terminal:
unset WEBROOT 
```
## Run

```directly run in terminal:
$ ./blackjack
```

Enjoy your game at:
[http://server_ip:8080/](http://server_ip:8080/)


