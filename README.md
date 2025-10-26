# Movie Search REST API Exercise 

## Install and Run Project on Windows

### Install Windows Subsystem Linux (WSL 2)

- Open `powershell` as admin and run the below command
```
wsl --install
```
- Restart your PC and go back to `powershell` and run the below command to make sure `WSL 2` has been installed correctly:
```
wsl -l -v
```
- You should see an output like below:
```
  NAME              STATE           VERSION
* Ubuntu            Running         2
  docker-desktop    Running         2
```

### Install Docker Desktop for Windows

- Docker desktop requires `WSL 2`
- Download Docker Desktop for [windows](https://docs.docker.com/desktop/setup/install/windows-install/)
- Follow installation instrunctions in `docker.com/desktop/setup/install/windows-install`

### Install openjdk-17 in WSL 2 (ubuntu)

- Go back to `powershell` and run `WSL 2` using the below command:
```
wsl
```
- `WSL 2` will ask to set a root password the first time it's run
- After setting root password you should see bash shell home dir
```
youruser@LAPTOP-NNNNN:/mnt/c/Users/youruser/
```
- Note that from this point onwards all the command line commands should be done in `WSL 2` bash shell
- Run the below commands to install openjdk-17
``` 
sudo apt-get update
sudo apt install openjdk-17-jdk -y
```
- Make sure jdk has been installed correctly and java home path exists by running the below commands:
```
java -version
ls -la /usr/lib/jvm/java-17-openjdk-amd64
```
- Export `JAVA_HOME` and add JDK 17 to the `PATH` by using the below commands:
```
echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" >> ~/.bashrc
echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> ~/.bashrc
```
- Make sure `.bashrc`'s last two lines are correctly exporting `JAVA_HOME` and `PATH`. The below are suggested commands using `vim`:
```
vim ~/.bashrc
ESC
:q
```
- Source `.bashrc` by using below command:
```
source ~/.bashrc
```

### Clone git repository with Movie Search Demo application
```
git clone https://github.com/alejandro-bulgaris/movie-search-api-exercise.git
```
- The folder structure of `movie-search-api-exercise` project should be as below:
  - The `demo` folder is a Spring Boot `REST API` with the features requested
  - The `angular` folder is the add-on Angular Single Page Application
```
movie-search-api-exercise/
├── demo/
│   ├── compose.yaml
│   └── src
├── angular/
│   └── ...
├── MovieSearchDemo_BrunoCollection.json
├── .editorconfig
├── .gitignore
└── README.md
```

### Download movies json data from [prust](https://github.com/prust/wikipedia-movie-data) repo into "resources" folder
```
cd movie-search-api-exercise/demo/src/main/resources
curl -L -o movies.json https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json
```

### Run Spring Boot Package
- Go to project `demo` dir and run `Spring Boot` package command:
```
cd movie-search-api-exercise/demo
./mvnw package
```

### Update REST API user and password
- Edit `compose.yaml` file in `demo` to set `APP_USER` and `APP_PASSWORD`

### Build docker image and run
- Make sure Docker Desktop is running
- Still in `demo` dir run the below commands:
```
docker build -t springio/gs-spring-boot-docker .
docker compose up -d
```

### Navigate to localhost:8080
- Check that the backend API is running. The below is an endpoint without authentication to make it simpler to test that installation has been successful:
```
http://localhost:8080
```

### Use Postman or Bruno or a Browser to call the REST API
- Note there is a Bruno collection in project root folder named `MovieSearchDemo_BrunoCollection.json`
- If using Postman or Bruno set Basic Auth with user and password set in `compose.yaml` file 
- Example request:
```
http://localhost:8080/api/movies/file/search?year=1996&genre=Drama&title=Arr
```
- Example response:
```
[
  {
    "id": null,
    "title": "Carried Away",
    "year": 1996,
    "genre": null,
    "cast": [
      "Dennis Hopper",
      "Amy Irving",
      "Amy Locane",
      "Gary Busey"
    ],
    "genres": [
      "Drama"
    ],
    "href": "Carried_Away_(1996_film)",
    "extract": "Carried Away is a 1996 American English language film directed by Brazilian Bruno Barreto. It is based on the novel Farmer by Jim Harrison.",
    "thumbnail": "https://upload.wikimedia.org/wikipedia/en/9/98/Carried_away_film.jpg",
    "thumbnail_width": 271,
    "thumbnail_height": 367
  },
  {
    "id": null,
    "title": "The People vs. Larry Flynt",
    "year": 1996,
    "genre": null,
    "cast": [
      "Woody Harrelson",
      "Courtney Love",
      "Edward Norton"
    ],
    "genres": [
      "Biography",
      "Drama"
    ],
    "href": "The_People_vs._Larry_Flynt",
    "extract": "The People vs. Larry Flynt is a 1996 American biographical drama film directed by Miloš Forman, chronicling the rise of pornographer Larry Flynt and his subsequent clash with religious institutions and the law. It stars Woody Harrelson, Courtney Love as his wife Althea, and Edward Norton as his attorney Alan Isaacman. The screenplay, written by Scott Alexander and Larry Karaszewski, spans about 35 years of Flynt's life, from his impoverished upbringing in Kentucky to his court battle with Reverend Jerry Falwell, and is based in part on the U.S. Supreme Court case Hustler Magazine v. Falwell.",
    "thumbnail": "https://upload.wikimedia.org/wikipedia/en/b/b5/People_vs_larry_flynt_poster.jpg",
    "thumbnail_width": 254,
    "thumbnail_height": 393
  }
]
```

