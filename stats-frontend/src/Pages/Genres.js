import { useState } from "react";

export default function Genres () {
    const [genres, setGenres] = useState(Array().fill(null))

    async function genreRequest(timeFrame) {
        const response = await fetch(`http://localhost:8080/topGenres?timeFrame=${timeFrame}`)

        const result = await response.json();

        console.log(result)

        setGenres(result.map(genre => <li key={genre.id}><p>{genre.genreName}</p><p>{genre.count}</p></li>))
    }

    return (
        <div>
            <h1>Top Genres</h1>
            <p>Please select a time frame</p>
            <button onClick={() => genreRequest('short_term')}>Last month</button>
            <button onClick={() => genreRequest('medium_term')}>Last 6 months</button>
            <button onClick={() => genreRequest('long_term')}>All time</button>
            <ul>{genres}</ul>
        </div>
    )
}