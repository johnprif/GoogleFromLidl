# GoogleFromLidl
> A desktop search‑engine app over Twitter comments, built in Java Swing with Apache Lucene full‑text indexing. Scan large volumes of tweets, perform keyword queries, and explore results in a rich GUI.  

[Download Latest Release (screencast)](https://github.com/johnprif/GoogleFromLidl#demo)

## 📋 Table of Contents

1. [Overview](#overview)  
2. [Features](#features)  
3. [Screenshots](#🖼️-screenshots)  
4. [Technologies](#🛠️-technologies)  
5. [Getting Started](#🚀-getting-started)  
6. [Usage](#usage)  
7. [Architecture](#🏗-architecture)  
8. [Contributing](#🤝-contributing)  
9. [License](#📄-license--contact) 
10. [Contact](#📬-contact) 
11. [Acknowledgements](#🙏-acknowledgements)  
12. [Changelog](#📝-changelog)  

## Overview

“GoogleFromLidl” is a **Java Swing** desktop application that lets you index and search Twitter comments using **Apache Lucene**. It loads raw tweet data, builds an inverted index, and provides instant full‑text queries with ranking. Use it to explore public sentiment, debug NLP pipelines, or prototype search features in pure Java.

## Features

- **Full‑text indexing** of tweet JSON files using Lucene’s `StandardAnalyzer` for tokenization and stemming.  
- **Advanced query syntax**: boolean operators, phrase search, wildcard, fuzzy matching.  
- **Swing‑based GUI**: sortable tables, live search box, and result highlighting.  
- **Configurable indexing**: select fields (user, date, text), adjust analyzer settings.  
- **Export results** to CSV for downstream analysis.  

## 🖼️ Screenshots

<p align="center">  
  <img src="https://user-images.githubusercontent.com/56134761/217270067-7924a16b-fbf3-4739-a27b-91b459b6941c.png" alt="Search Results" width="400"/>  
</p>

## 🛠️ Technologies

- **Language**: Java 11 
- **GUI**: Java Swing (MVC pattern) 
- **Search Engine**: Apache Lucene 8.x
- **Logging**: SLF4J + Logback  

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher installed  

### Installation

```bash
# Clone the repo
git clone https://github.com/johnprif/GoogleFromLidl.git
cd GoogleFromLidl
```

## 💡 Usage
1. **Load Tweets:** File -> Open JSON directory (each file contains tweet objects).
2. **Index:** Click "Build Index" to parse and index all tweets.
3. **Search:** Enter keywords or expressions in the search bar then press Enter.
4. **Inspect:** Click any result to view full tweet details and metadata.
5. **Export:** Results -> Export to CSV.

## 🏗 Architecture
```plaintext
+----------------+      +-----------------+      +------------------+
| Swing GUI      | <--> | Controller      | <--> | Lucene Index API |
+----------------+      +-----------------+      +------------------+
                                 |
                                 v
                       +--------------------+
                       | Tweet JSON Parser  |
                       +--------------------+
```
- **MVC pattern** separates UI (Swing GUI), control logic (Controller), and search engine integration (Lucene Index API) into distinct layers for modularity and testability.
- **Swing GUI** is implemented as the View in MVC, rendering components on a single UI thread and dispatching user events to the Controller.
- **Controller** mediates between the GUI and the Lucene model: it builds quaries, trigger indexing/search operations, and updates the view with results.
- **Lucene Index API** (Model) uses `FSDirectory` to persist the inverted index on disk for durability and fast lookup.
- **Tweet JSON Parser** converts raw tweet JSON into Lucene `Document` instances with fields (text, user, date) for indexing.
- **Analyzer:** `StandardAnalyzer` (with optional custom stop-word list) tokenizes, lower-cases, and filters terms during both indexing and querying.

Lucene's `FSDirectory.open(Paths.get(indexPath))` chooses the optimal file-system implementation (SimpleFSDirectory, NIOFSDirectory, or MMapDirectory) based on the environment.

## 🤝 Contributing
Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/foo`)
3. Commit your changes (`git commit -m "Add feature"`)
4. Push (`git push oprigin feature/foo`)
5. Open a Pull Request

## 📄 License
**MIT License.** See [LICENSE](https://github.com/johnprif/GoogleFromLidl/blob/main/LICENSE)

## 📬 Contact
- GitHub: [johnprif](https://github.com/johnprif)
- Email: [giannispriftis37@gmail.com](mailto:giannispriftis37@gmail.com)
- Phone: [+306940020178](tel:+306940020178)

## 🙏 Acknowledgements
- **[othneildrew/Best-README-Template](https://www.hatica.io/blog/best-practices-for-github-readme/?utm_source=chatgpt.com)** for structure inspiration.
- **[FreeCodeCamp](https://github.com/Louis3797/awesome-readme-template?utm_source=chatgpt.com)** article on witing good READMEs
- **[GitHub Docs](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax?utm_source=chatgpt.com)** on basic Markdown syntax and TOC support.
- **[Hatica blog](https://www.hatica.io/blog/best-practices-for-github-readme/?utm_source=chatgpt.com)** on eye-catching README design.

## 📝 Changelog
- **v1.0** (2023-04-27): Initial release.
