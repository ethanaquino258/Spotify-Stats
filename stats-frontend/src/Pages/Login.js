// import { useEffect, useState } from "react";
import {Navigate} from 'react-router-dom';


export default function Login ({user, login}) {
    return (
        <div id="login">
            <button id="loginButton" onClick={login}>Login to Spotify</button>
        </div>
    );
    
}