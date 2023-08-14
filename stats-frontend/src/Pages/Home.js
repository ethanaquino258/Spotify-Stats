import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Home() {
    const navigate = useNavigate();
  
    return (
      <div className="Home">
        <h1 id="title">Spotify Stats</h1>
        <button class="redirectButton" onClick={() => {navigate('/songs')}}>Songs</button>
        <button class="redirectButton" onClick={() => {navigate('/artists')}}>Artists</button>
        <button class="redirectButton" onClick={() => {navigate('/genres')}}>Genres</button>
        <button class="redirectButton" onClick={() => {navigate('/library')}}>Library</button>
      </div>
    );
  }