# SpeechDrop

**Fast, efficient, and secure document sharing for debate rounds.**

SpeechDrop is a lightweight web application designed to simplify the process of sharing debate files (e.g., DOC, DOCX, ODT, PDF, TXT, RTF) in real time. It uses [Vert.x](https://vertx.io/) on the backend, [Vue.js](https://vuejs.org/) on the frontend, and leverages WebSockets (via Vert.x EventBus) for instant updates.

*This is a community-driven project originally built by Yunyu Lin and extended by contributors. It was forked in January 2025 after the original repo appeared abandoned, and the domain began redirecting to a bakery*

---

## Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Installation & Development](#installation--development)
  - [Prerequisites](#prerequisites)
  - [Quick Start (Local)](#quick-start-local)
  - [Running via Docker](#running-via-docker)
- [Configuration](#configuration)
- [Deployment](#deployment)
  - [Deploying on Fly.io](#deploying-on-flyio)
  - [Persistent File Storage](#persistent-file-storage)
- [Usage](#usage)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **Room-based file sharing** – Quickly create or join rooms by entering a room code.
- **Drag-and-drop uploads** – Simply drag files onto the UI to upload them.
- **Auto-updating file list** – Everyone in the room sees new files instantly, no refresh required.
- **Easy archiving** – Download a ZIP of all files in the room with a single click.

---

## Screenshots

![Home Screen](https://i.imgur.com/VxTlwsS.png) 
![Room View](https://i.imgur.com/2q0zyTc.png) 

---

## Tech Stack

- **Backend**: [Java 8](https://www.oracle.com/java/technologies/downloads/), [Vert.x](https://vertx.io/)
- **Frontend**: [Vue.js 2](https://vuejs.org/), [Webpack 3](https://webpack.js.org/)
- **Build**: [Maven](https://maven.apache.org/), [Yarn](https://yarnpkg.com/)

---

## Installation & Development

### Prerequisites

- **Java 8** (or later, though the Dockerfile uses 8)
- **Maven 3.6+**
- **Node.js & Yarn** (the Maven frontend plugin automatically installs Node and Yarn locally if needed)

### Quick Start (Local)

1. **Clone** the repository:
   ```bash
   git clone https://github.com/<your-username>/speechdrop.git
   cd speechdrop
   ```

2.	Build the project (including frontend assets):
    ```
    mvn clean package
    ```

	This command installs Node/Yarn locally (if not present) and builds the frontend into frontend/dist.

3.	Run the resulting JAR:

    ```
    java -Dfile.encoding=UTF-8 -jar target/SpeechDrop-1.0-SNAPSHOT.jar
    ```

By default, it listens on port 8080 (configurable via the PORT environment variable).

4.	Open your browser at:
    ```
    http://localhost:8080/
    ````
    You should see the SpeechDrop home screen.

### Running via Docker

1.	Build the Docker image:

```
docker build -t speechdrop:latest .
```


2.	Run the container:

```
docker run -it --rm -p 8080:8080 speechdrop:latest
```

3.	Access the app at:

```
http://localhost:8080/
```

### Configuration

SpeechDrop can be configured via environment variables (this replaces the older config.json approach):

- PORT: Port on which the server listens (default: 8080)
- HOST: Host address to bind to (default: 0.0.0.0)
- CSRF_SECRET: Random string for CSRF protection
- MEDIA_URL: Base URL used in the HTML for serving file downloads (default: https://media.speechdrop.net/uploads/)
- DEBUG_MEDIA_DOWNLOADS: Whether to serve files from local disk (true) or rely on external hosting (false)

## Deployment

### Deploying on Fly.io
1.	Install the Fly CLI.
2.	Initialize a Fly app (if you haven’t yet):

```
fly launch
```

or configure an existing Fly app via `fly.toml`.

3.	Set your secrets:

```
fly secrets set CSRF_SECRET=$(uuidgen)
fly secrets set DEBUG_MEDIA_DOWNLOADS=true
# ...and so on
```

4.	Deploy:

```
fly deploy
```

Fly will build your Dockerfile, start a VM, and launch your container.

Note: By default, Fly machines have ephemeral filesystems. Attaching a Fly volume works only if you have a single machine.

## Usage
1.  **Create a Room**: Enter a name on the home page, click “Make Room.”
2.	**Invite Others**: Share the room code.
3.	**Upload Files**: Drag-and-drop them onto the upload area. Everyone sees updates instantly.
4.	**Download**: Either click on individual files or use “Download All Files” to get a ZIP archive.

## Roadmap

- S3/MinIO integration for multi-instance file hosting
- Update beyond Java 8. 
- Update frontend dependencies

## Contributing

Pull requests are welcome!
1.  Fork and clone this repository
2.	Create a feature branch: git checkout -b feature/my-feature
3.	Commit your changes: git commit -m 'Add new feature'
4.	Push to your branch: git push origin feature/my-feature
5.	Open a Pull Request

## License

MIT License

See [LICENSE](/LICENSE) for details.

