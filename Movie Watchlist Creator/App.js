import { useState } from "react";
import Search from "./components/Search";
import Results from "./components/Results";
import Result from "./components/Result";
import axios from "axios";
import Popup from "./components/Popup";
import Watchlist from "./components/Watchlist";
function App() {
  const [state, updateState] = useState({
    input: "",
    result: [],
    chosen: {},
    favorites:""
});
  const url = "http://www.omdbapi.com/?apikey=25f47d11";
  const search = (e) => {
    if(e.key === "Enter"){
      axios(url + "&s=" + state.input).then(({data}) => {
        let result = data.Search;
        updateState(prev =>{
          return {...prev, result: result}
        })
      })
    }
  }

  const openPopup = id =>{
    axios(url + "&i=" + id).then(({data})=>{
      let result = data;
      console.log(result)
      updateState(prev => {
        return{...prev,chosen: result}
      })

    });
  }

  const addFavorite = () =>{
    updateState(prev => {
      let chosen = state.chosen;
      const favoriteArray = prev.favorites.split("///");
      return{...prev, favorites:prev.favorites + "///" + chosen.Title}}
    );
    console.log(state.favorites);
  }

  const closePopup = () => {
    updateState(prev =>{
      return {...prev,chosen:{}}
    });
  }
  const handleInput = (e) => {
    let s = e.target.value;
    updateState(prev =>{
      /*everything stays the same except the input is updated */
      return{...prev, input:s}
    });
    console.log(state.s)

  }


  return (
    <div className="App">
      <section className="left">
      <header className="App-header">
        <h1>Welcome to Neha's movie search engine!</h1>
        <p>Search for a movie and click on the poster to learn more information! If this
          movie seems like something you would like, add it to your watchlist!
        </p>
        {console.log(state.favorites)}
      </header>

      <main>
        <Search handleInput={handleInput} search={search} />
        <Results results={state.result} openPopup={openPopup}/>

        {(typeof state.chosen.Title != "undefined") ? <Popup chosen={state.chosen} closePopup
        ={closePopup} addFavorite={addFavorite}/> : false}
        
      </main>
      </section>
      <section className="right">
        <h1>Watchlist!</h1>
        {state.favorites.split("///").map((title) => <p>{title}</p>)}
      </section>
    </div>
  );
}

export default App;
