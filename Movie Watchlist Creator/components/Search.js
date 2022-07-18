import React from "react";

function Search({handleInput, search}){
    return(
        <section className="searchBxwrp">
            <input type="text" className="searchBx" placeholder="please enter a movie to search for"
            onChange={handleInput}
            onKeyPress={search}>
            </input>
        </section>

    )
}

export default Search
