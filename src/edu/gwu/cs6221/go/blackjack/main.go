package main

import (
	"net/http"
	"os"

	"github.com/codegangsta/negroni"
	"github.com/gorilla/mux"
	//"github.com/unrolled/render"
	"regexp"
	"log"
	"encoding/json"
)

type FileServerHanlder struct {
	fileserver http.Handler
	jsFile *regexp.Regexp
}

var myGame *Game

func (handler *FileServerHanlder)ServeHTTP(w http.ResponseWriter, r *http.Request) {
	ruri := r.RequestURI
	if handler.jsFile.MatchString(ruri) {
		w.Header().Set("Content-Type", "application/javascript")
	}
	handler.fileserver.ServeHTTP(w, r)
}

// NewServer configures and returns a Server.
func NewServer() *negroni.Negroni {
	/*
	formatter := render.New(render.Options{
		IndentJSON: true,
	})*/

	n := negroni.Classic()
	mx := mux.NewRouter()

	webRoot := os.Getenv("WEBROOT")
	if len(webRoot) == 0 {
		if root, err := os.Getwd(); err != nil {
			panic("Could not retrive working directory")
		} else {
			webRoot = root
			//fmt.Println(root)
		}
	}
	// The directory to serve.
	log.Println(webRoot + "/resources/")
	fsHandler := new(FileServerHanlder)
	fsHandler.fileserver = http.FileServer(http.Dir(webRoot + "/resources/"))
	fsHandler.jsFile = regexp.MustCompile("\\.js$")

	mx.HandleFunc("/game/start", gameStartHandler).Methods("GET")
	mx.HandleFunc("/player/hit", playerHitHandler).Methods("GET")
	mx.HandleFunc("/player/stand", playerStandHandler).Methods("GET")
	mx.PathPrefix("/").Handler(fsHandler)

	n.UseHandler(mx)
	return n
}

func gameStartHandler(w http.ResponseWriter, r *http.Request) {
	log.Println("Game start!")
	myGame.Start()
	responseGameStatus(w, r)
}

func playerHitHandler(w http.ResponseWriter, r *http.Request) {
	log.Println("Player hit!")
	myGame.PlayerHit()
	responseGameStatus(w, r)
}

func playerStandHandler(w http.ResponseWriter, r *http.Request) {
	log.Println("Player Stand!")
	myGame.PlayerHold()
	responseGameStatus(w, r)
}

func responseGameStatus(w http.ResponseWriter, r *http.Request) {
	js, err := json.Marshal(myGame.getStatus())
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	w.Write(js)
}



func main() {
	/*
	f, err := os.OpenFile("logFile", os.O_RDWR | os.O_CREATE | os.O_APPEND, 0666)
	if err != nil {
		return
	}
	defer f.Close()

	log.SetOutput(f)
	*/
	myGame = new(Game)
	myGame.Init()
	server := NewServer()
	server.Run(":8080")
}
