import { useState } from "react";

export default function Songs() {
    const [songs, setSongs] = useState(Array(50).fill(null))

    async function songRequest(timeFrame) {
        const response = await fetch(`http://localhost:8080/topTracks?timeFrame=${timeFrame}`)

        const result = await response.json();

        console.log(result);
        //performers is an array, need another map for that
        //map inside of a map is kind of messy
        setSongs(result.map(song => <li key={song.songUri}><p>{song.songName}</p><p>{song.performers.map(performer => performer.performerName)}</p><img src={song.imageUrl} alt= "no image"/></li>))
    }

    
    return (
        <div>
            <h1>Top Songs</h1>
            <p>Please select a time frame</p>
            <button onClick={() => songRequest('short_term')}>Last month</button>
            <button onClick={() => songRequest('medium_term')}>Last 6 month</button>
            <button onClick={() => songRequest('long_term')}>All time</button>
            <ol>{songs}</ol>
        </div>
    )
    
}