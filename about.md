---
layout: page
title: About the course
description: Learning outcomes, format, the tools you will use, and how the weeks build on each other.
permalink: /about/
---

Software engineering applies systematic methods to building and evolving software under real constraints. Programming is a central part of that work, alongside understanding users, making design decisions, verifying behaviour, collaborating, and keeping software useful after release. This course combines engineering principles with Java programming: understanding requirements, designing software, implementing and testing it, collaborating through Git, and delivering and maintaining a working application.

| Area | Main question | Purpose |
| --- | --- | --- |
| **Requirements and planning** | What should we build, and how should we organise the work? | Establish user needs, constraints, priorities, and manageable increments. |
| **Design and construction** | How should the software be structured and implemented? | Create understandable code with clear responsibilities and interfaces. |
| **Verification and improvement** | Does it behave correctly, and can we change it safely? | Find defects, test assumptions, review changes, and improve maintainability. |
| **Delivery and operation** | How do we release, observe, and maintain it? | Make execution reproducible, automate delivery, and respond to failures. |

These activities recur as software evolves. A changed requirement may affect the design, code, tests, deployment configuration, and documentation.

## Learning outcomes
{: #outcomes }

By the end of the course, you should be able to:

- Translate stakeholder needs into clear requirements, acceptance criteria, and behavioural contracts.
- Select a development process, plan small increments, estimate effort, and manage risks and changes.
- Collaborate using Git, GitHub, pull requests, issue tracking, and constructive code review.
- Build a Java project with Maven, explain dependency resolution and artifacts, and compare Gradle and npm, recognising Yarn as an alternative package manager.
- Implement modular software using functions, classes, interfaces, and appropriate design principles.
- Read and create focused UML diagrams, and maintain Mermaid diagrams alongside code.
- Write meaningful tests, investigate defects, and refactor without changing intended behaviour.
- Evaluate architecture against reliability, availability, scalability, security, and maintainability requirements.
- Use Docker, Compose, and GitHub Actions to build reproducible environments and automate delivery.
- Diagnose operational failures and maintain software using logs, monitoring, tests, and documentation.
- Apply professional responsibility to privacy, accessibility, open-source reuse, and AI-assisted development.

## Format and prerequisites
{: #format }

The course carries **6 ECTS** and a **150-hour workload**, including 13 lecture hours and 26 laboratory hours. Teaching combines explanations, live programming demonstrations, and hands-on practice in Java. Examples progress from small functions to collaborating modules and a deployable application. They use a familiar application domain so you can focus on engineering decisions.

You should have completed a course in a high-level programming language or object-oriented programming, and have **B1 English** for reading technical materials. You should understand variables, control flow, functions, and basic collections. {% include week.html key="foundations" cap=true %} refreshes programming foundations; classes and interfaces are revisited before object-oriented design in {% include week.html key="design" %}.

The [course contents]({{ '/#curriculum' | relative_url }}) list lecture topics only. **Lab instructions and assignments are published in the SANGU LMS**, which is also where to ask questions about the course material. Assessment arrangements are provided separately. Individual understanding remains essential: you are responsible for explaining and verifying what you submit, including work done with teammates or AI tools.

## What to install, and when
{: #install }

Set up each tool before the week that introduces it. Maven is the main build tool and Gradle a brief comparison, so you learn one workflow thoroughly rather than maintaining the same application in several tools. npm arrives when a tool first needs it.

| Tool | Introduced | Notes |
| --- | --- | --- |
| JDK 21 or newer | {% include week.html key="foundations" cap=true %} | Install a full JDK, not only a runtime, and check it with `javac --version`. See [Getting started with Java](https://dev.java/learn/getting-started/). |
| Editor or IDE | {% include week.html key="foundations" cap=true %} | An IDE is convenient, but a text editor and a terminal are enough. |
| Git and a GitHub account | {% include week.html key="git" cap=true %} | Install Git, and create a GitHub account if you do not have one. |
| Maven | {% include week.html key="build-tools" cap=true %} | Install it, or run it through a project's Maven Wrapper. JUnit, the testing library, comes through Maven, so it needs no installation. |
| Docker with Compose | {% include week.html key="docker" cap=true %} | Docker Desktop needs hardware virtualization, and on Windows it normally uses WSL 2. Check early that it runs on your computer. |
| Node.js with npm | {% include week.html key="delivery" cap=true %} | Runs the Mermaid CLI, which exports diagrams as images; npm is also compared with Maven. |

GitHub Actions runs on GitHub, and GitHub renders Mermaid diagrams in Markdown, so neither needs to be installed. The database needs no installation either: during development it runs inside the application as a Maven dependency, and from {% include week.html key="docker" %} as a service in Docker. Dependency-installation scripts can execute code, so use trusted packages and review project scripts before running them.

## How the weeks build on each other
{: #sequence }

Topics build on one another: you learn to express behaviour before testing it, establish tests before refactoring, and understand runtime environments before automating deployment.

Programming foundations support Git collaboration and code review. {% include week.html key="build-tools" cap=true %} then introduces project structure, dependency management, repeatable builds, and the first automated tests before modular application development. Markdown and GitHub come before Mermaid publishing. Requirements establish the behaviour that planning, design, and tests must address. Classes and interfaces precede class-level testing; tests and debugging precede refactoring and CI.

Architecture then connects modules into a system with an HTTP interface and a database, and gives security analysis concrete boundaries. Runtime environments and containers precede delivery automation. Logging and maintenance build on a system you can already understand, test, and release. {% include week.html key="operations" cap=true %} combines maintenance with a brief integrated review, keeping the course at {{ site.data.weeks.size }} teaching weeks.

## The running example
{: #running-example }

Lessons develop one application throughout the course: a pizza-party planner for a student club. It starts as a small calculation of how many whole pizzas to order and gains one engineering technique each week, so every new idea is applied to code you already know. The first lesson also raises questions, such as who places the order, how to feed vegetarian guests, and what the app may store about allergies, that later weeks answer.

The code lives in the [pizza-party repository]({{ site.running_example_url }}), with a Git tag for its state at the end of each week. `git checkout week-07` shows the application at the end of that week, and `git diff week-06 week-07` shows what the week changed. Where a week needs plumbing, such as the HTTP server or database setup, the repository supplies it, so lectures can focus on the engineering decisions.

| Week | The application gains |
| --- | --- |
{% for week in site.data.weeks -%}
| {% include week.html key=week.key cap=true %} | {{ week.example }} |
{% endfor %}

## Diagrams: UML and Mermaid
{: #diagrams }

UML and Mermaid help connect requirements, design decisions, and code throughout the course. Select a diagram according to the question it answers. Keep models small enough to read and specific enough to compare with the implementation.

| View | Engineering question | First taught |
| --- | --- | --- |
| **UML use case** | Who uses the system, and for which goals? | {% include week.html key="requirements" cap=true %} |
| **UML activity** | How does a workflow proceed and branch? | {% include week.html key="requirements" cap=true %} |
| **UML class** | What responsibilities and relationships exist between types? | {% include week.html key="design" cap=true %} |
| **UML state machine** | Which lifecycle transitions are valid? | {% include week.html key="testing" cap=true %} |
| **UML component** | What are the main parts and their interfaces? | {% include week.html key="architecture" cap=true %} |
| **UML sequence** | How do collaborators complete a scenario? | {% include week.html key="architecture" cap=true %} |
| **UML deployment** | Where do artifacts execute, and how do environments connect? | {% include week.html key="docker" cap=true %} |

Mermaid coverage progresses through `flowchart` ({% include week.html key="requirements" %}), `classDiagram` ({% include week.html key="design" %}), `stateDiagram-v2` ({% include week.html key="testing" %}), and `sequenceDiagram` ({% include week.html key="architecture" %}), followed by infrastructure views and web publishing. UML is a modelling language; Mermaid is an authoring and rendering tool. General Mermaid flowcharts can illustrate workflows and infrastructure without claiming to use formal UML activity or deployment notation.

GitHub renders supported Mermaid syntax in Markdown code fences labelled `mermaid`. Preview diagrams on the target platform and check its renderer version. A GitHub Pages site needs its own Mermaid integration or exported images; repository Markdown support does not automatically configure a website. See the [GitHub diagram documentation](https://docs.github.com/en/get-started/writing-on-github/working-with-advanced-formatting/creating-diagrams) and [Mermaid user guide](https://mermaid.js.org/intro/getting-started.html).

## Resources
{: #resources }

- **Core textbook:** Pankaj Jalote, [A Concise Introduction to Software Engineering: With Open Source and GenAI](https://link.springer.com/book/10.1007/978-3-031-74318-4), second edition, 2025.
- **Git:** [Pro Git](https://git-scm.com/book/en/v2).
- **Build tools:** [Maven introduction](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html), [Gradle concepts](https://docs.gradle.org/current/userguide/gradle_basics.html), [npm scripts guide](https://docs.npmjs.com/cli/v11/using-npm/scripts/), [npm ci reference](https://docs.npmjs.com/cli/v11/commands/npm-ci/), and [Yarn introduction](https://yarnpkg.com/getting-started).
- **Software construction:** [MIT 6.102 materials, Spring 2026](https://web.mit.edu/6.102/www/sp26/).
- **Agile development:** [The Scrum Guide](https://scrumguides.org/scrum-guide.html).
- **Diagrams:** [Mermaid documentation](https://mermaid.js.org/intro/getting-started.html).
- **Containers:** [Docker getting-started documentation](https://docs.docker.com/get-started/).
- **Automation:** [GitHub Actions documentation](https://docs.github.com/en/actions).
- **Application security:** [OWASP Top 10](https://owasp.org/projects/top-ten).

The agenda draws on the SANGU syllabus and comparisons with recent courses at [MIT](https://web.mit.edu/6.102/www/sp26/), [UC Berkeley](https://cs169.org/fa26/syllabus/), [NUS](https://nus-cs2103-ay2526s1.github.io/website/), [Edinburgh](https://opencourse.inf.ed.ac.uk/inf2-sepp/schedule), and [UNSW](https://cgi.cse.unsw.edu.au/~cs1531/25T1/dashboard). The scope and sequence are adapted to this course's {{ site.data.weeks.size }} teaching weeks.
