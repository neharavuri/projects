import React, { useState } from "react";

function Popup({chosen, closePopup, addFavorite}){
    const[disabled, setDisable] = useState(false)
    const handleClick = () => {
        setDisable(true);
        addFavorite();

    }
    return(
        <section className="popup">
            <div className="details">
                <h2>{chosen.Title} ( {chosen.Year} )</h2>
                <p>Rating: {chosen.imdbRating}</p>
                <img src={chosen.Poster}></img>
                <p>{chosen.Plot}</p>
                <button className="close" onClick={closePopup}>Close</button>
                <button className="favorite" onClick={handleClick} disabled={disabled}>Add to Watchlist</button>
            </div>
        </section>
    )
}

export default Popup
