===== Setup =====
1. Run initdb
2. Navigate to demo folder and run 'mvn clean install'
3. Run mvn spring-boot:run

===== Notes =====
- Included a simple home page UI on http://localhost:8080/ for navigation
- All parameters are passed in via GET method
- The only extended delete query (checks other database ) is deleting genre whereby it will set other database to 0

===== Links =====
Homepage
http://localhost:8080/

Genres
http://localhost:8080/demo/viewallgenre
http://localhost:8080/demo/addgenre?name=bass
http://localhost:8080/demo/deletegenre?name=bass
http://localhost:8080/demo/updategenre?id=1&newname=Jazz

Songs
http://localhost:8080/demo/viewallsong
http://localhost:8080/demo/songinfo?id=1
http://localhost:8080/demo/addsong?name=Senbonsakura&genreid=1
http://localhost:8080/demo/deletesong?id=1
http://localhost:8080/demo/updatesong?id=1&newname=Okkusenman&newgenreid=1

Listeners
http://localhost:8080/demo/viewalllistener
http://localhost:8080/demo/addlistener?name=KizunaAi
http://localhost:8080/demo/deletelistener?id=1
http://localhost:8080/demo/updatelistener?id=1&newname=MiraiAkari

ListenerGenre
http://localhost:8080/demo/viewalllistenergenre

===== Databases and tables =====
=> Radiostation
   - Genre
   - Song
   - Listener
   - ListenerGenre
      
Genre 
ID, Name

Song
ID, GenreID, Name

Listener
ID, Name
  
ListenerGenre
ID, GenreID, Priority