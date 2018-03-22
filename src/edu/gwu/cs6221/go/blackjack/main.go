package main

import (
	"net/http"
	"os"

	"github.com/codegangsta/negroni"
	"github.com/gorilla/mux"
	"github.com/unrolled/render"
	"regexp"
	"log"
)


type FileServerHanlder struct {
	fileserver http.Handler
	jsFile *regexp.Regexp
}

func (handler *FileServerHanlder)ServeHTTP(w http.ResponseWriter, r *http.Request) {
	ruri := r.RequestURI
	if handler.jsFile.MatchString(ruri) {
		w.Header().Set("Content-Type", "application/javascript")
	}
	handler.fileserver.ServeHTTP(w, r)
}

// NewServer configures and returns a Server.
func NewServer() *negroni.Negroni {

	formatter := render.New(render.Options{
		IndentJSON: true,
	})

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

	fsHandler := new(FileServerHanlder)
	fsHandler.fileserver = http.FileServer(http.Dir(webRoot + "/resources/"))
	fsHandler.jsFile = regexp.MustCompile("\\.js$")

	mx.HandleFunc("/game/start", gameStartHandler(formatter)).Methods("GET")
	mx.HandleFunc("/player/hit", playerHitHandler(formatter)).Methods("GET")
	mx.HandleFunc("/player/stand", playerStandHandler(formatter)).Methods("GET")
	mx.PathPrefix("/").Handler(fsHandler)

	n.UseHandler(mx)
	return n
}


func gameStartHandler(formatter *render.Render) http.HandlerFunc {
	log.Println("Game start!")
	return func(w http.ResponseWriter, req *http.Request) {
		formatter.JSON(w, http.StatusOK, struct {
			ID      string `json:"id"`
			Content string `json:"content"`
		}{ID: "8675309", Content: "game start!"})
	}
}

func playerHitHandler(formatter *render.Render) http.HandlerFunc {
	log.Println("Player hit!")
	return func(w http.ResponseWriter, req *http.Request) {
		formatter.JSON(w, http.StatusOK, struct {
			ID      string `json:"id"`
			Content string `json:"content"`
		}{ID: "8675309", Content: "player hit!"})
	}
}

func playerStandHandler(formatter *render.Render) http.HandlerFunc {
	log.Println("Player stand!")
	return func(w http.ResponseWriter, req *http.Request) {
		formatter.JSON(w, http.StatusOK, struct {
			ID      string `json:"id"`
			Content string `json:"content"`
		}{ID: "8675309", Content: "player stand!"})
	}
}

func main() {
	server := NewServer()
	server.Run()
}