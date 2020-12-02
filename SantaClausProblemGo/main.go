package main

import (
	"fmt"
	"time"
)

type Santa struct {
	id uint16
	allDone chan string
}
type Reindeer struct {
	id uint16
	allDone chan bool
}
type Elves struct {
	id uint16
	allDone chan bool
}

// ch <- v Send v to channel ch
// v := <-ch //Receive from ch and assign value to v
func main() {
	s := Santa{id: 1, allDone: make(chan string)}
	r := Reindeer{id: 1, allDone: make(chan bool)}
	e := Elves{id: 1, allDone: make(chan bool)}
	go SantaWakeUp(s,r,e)
	go ReindeerWork(s,r)
	go ElvesHelp(s,e)
	_, _ = fmt.Scanln()
}

func SantaWakeUp(s Santa, r Reindeer, e Elves) {
	fmt.Println("Santa is Sleeping...")
	//send message to reindeer and Elves
	r.allDone <- true
	e.allDone <- true

	//message from reindeer or elves
	for msg := range s.allDone{
		fmt.Println("Santa Woke Up")
		if msg=="R" {
			fmt.Println("Santa is working with Reindeer, sending toys")
			time.Sleep(time.Second)
			fmt.Println("Santa delivered toys")
			r.allDone <- true
			time.Sleep(time.Second)
		}
		if msg=="E" {
			fmt.Println("Santa is helping the three Elves")
			time.Sleep(time.Second)
			fmt.Println("Santa finished helping the elves")
			e.allDone <- true
			time.Sleep(time.Second)
		}
		fmt.Println("Santa is Sleeping...")
	}
}
func ReindeerWork(s Santa, r Reindeer) {
	for {
		//receive the message from santa
		_ = <-r.allDone
		for i := 0; i <= 9; i++ {
			fmt.Println("Reindeer", i, "came back from holiday")
			if i == 9 {
				//send message to santa
				fmt.Println("We have 9 Reindeer, waking up santa")
				s.allDone <- "R"
			}
			time.Sleep(time.Second)
		}
	}
}

func ElvesHelp(s Santa, e Elves) {
	for {
		//receive the message from santa
		_ = <-e.allDone
		for i := 0; i <= 3; i++ {
			fmt.Println("Elves ", i, "joined")
			if i == 3 {
				fmt.Println("We have three elves now, waking up santa now")
				//send message to Santa
				s.allDone <- "E"
			}
			time.Sleep(time.Second)
		}
	}
}