import { useEffect, useState } from "react";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';
import randomColor from 'randomcolor';

export default function Library() {
    const [genres, setGenres] = useState(Array().fill(null));
    const [genreNames, setGenreNames] = useState(Array().fill(null));
    const [songCount, setSongCount] = useState(Array().fill(null));

    ChartJS.register(ArcElement, Tooltip, Legend);

    async function genresRequest() {
        const response = await fetch(`http://localhost:8080/allGenres`);
        const result = await response.json();
        setGenres(result);

        setGenreNames(result.map(genre => {return genre.genreName}));
        setSongCount(result.map(genre => {return genre.songCount}));
    }

    function colorArray() {
        const colors = []; 
        for (var i = 0; i < genreNames.length; i++) {
            colors.push(randomColor());
        }
        return colors;
    }

    const data = {
        labels: genreNames,
        datasets: [
            {
                label: '# of songs',
                data: songCount,
                backgroundColor: colorArray()
            },
        ],
    }

    return (
        <div>
            <h1>Library</h1>
            <button onClick={() => genresRequest()}>Click me</button>
            <Pie data={data} />
        </div>
    )
}