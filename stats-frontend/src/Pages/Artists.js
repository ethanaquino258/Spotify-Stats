import { useState } from "react";

export default function Artists() {
    const [artists, setArtists] = useState(Array(50).fill(null))

    async function artistRequest(timeFrame) {
        const response = await fetch(`http://localhost:8080/topArtists?timeFrame=${timeFrame}`)

        const result = await response.json();

        console.log(result);

        setArtists(result.map(artist => <li key={artist.performerUri}><p>{artist.performerName}</p><img src={artist.imageUrl} alt= "no image"/></li>))
    }

    
    return (
        <div>
            <h1>Top Artists</h1>
            <p>Please select a time frame</p>
            <button onClick={() => artistRequest('short_term')}>Last month</button>
            <button onClick={() => artistRequest('medium_term')}>Last 6 month</button>
            <button onClick={() => artistRequest('long_term')}>All time</button>
            <ol>{artists}</ol>
        </div>
    )
    
}