export default function SongItem({name, performer, image}) {
    return (
        <div class='song'>
            <p>{name}</p>
            <p>{performer}</p>
            <img src={image} alt="no image " />
        </div>
    );
}