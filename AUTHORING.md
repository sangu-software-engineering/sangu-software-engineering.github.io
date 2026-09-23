# Authoring guide

Conventions for writing lessons and examples on the course website. [README.md](README.md) covers running, structure, and publishing.

## Principles

- **No tool before its lecture.** Lessons and laboratory materials use only tools and notation introduced in their own or an earlier week. Diagrams are an exception in one direction: a lesson may *show* a diagram before students learn to *draw* one.
- **Small steps over setup.** Favour small, understandable changes over large amounts of framework setup.
- **Familiar situations.** Programming examples use everyday situations students already understand, such as exam scores, passwords, and parcels, so they can focus on engineering decisions.
- **One behaviour.** Working code, tests, diagrams, and documentation should describe the same behaviour.
- **Small, self-contained examples.** Each week brings its own short programs. The course has no single application that grows from week to week, so a chapter can be read and run on its own.
- **Every technique solves a problem.** Each new technique should solve an identifiable problem, and students should be able to explain its benefits, costs, and alternatives.
- **UML is the model; Mermaid is the tool.** UML is a modelling language; Mermaid authors and renders selected diagrams as text. Do not present a general Mermaid flowchart as a formal UML activity, component, or deployment diagram.

## Refer to weeks by key

Do not type week numbers in site content. Each entry in `_data/weeks.yml` has a stable `key`; refer to a week with the include:

```liquid
{% include week.html key="testing" %}            renders "week 7"
{% include week.html key="testing" cap=true %}   renders "Week 7", for the start of a sentence
```

The output links to the lesson once its `url` is set, and to the week's entry in the course contents before that. It works in Markdown, including table cells, and in HTML pages. An unknown key fails the build, so a typo cannot slip through. For the number of weeks, use `{{ site.data.weeks.size }}`.

Lessons identify their week the same way: a lesson's front matter sets `key`, not a number, and the lesson layout looks up the week number and the next lesson in `_data/weeks.yml`.

## Reorder or insert weeks

1. In `_data/weeks.yml`, insert or move entries and renumber `number` so it runs 1, 2, 3, and so on. Keep existing keys, and give a new week a `part` from `_data/parts.yml`. Each part's weeks must stay consecutive; the home page works out a part's week range from its first and last week. A week without a valid part fails the build.
2. Update the week list in the organization profile: repository `sangu-software-engineering/.github`, file `profile/README.md`. It is the only copy of the agenda outside this repository.
3. If a part or its topics change, update the "How this course works" table in `week-01.md`, which lists the parts and their topics.
4. Optionally rename lesson files, permalinks, and example folders so their `week-NN` names match the new numbers.
5. Build the site and check the home page, the About page, and each lesson's header and next-lesson link.

## Add a chapter

1. Create `week-02.md` using `week-01.md` as a structural example.
2. Set its front matter: `layout: lesson`, `title`, `description`, `permalink`, `key`, `category_label`, and `reading_time`. The header labels are optional: `language` (for example `Java`) and `format` (for example `Chapter + code walkthrough`). Optionally add `before_next_week`, one sentence on how to prepare, which the layout shows in the next-week box. Do not end the chapter with a paragraph about next week; that box already shows the next week's title and summary.
3. Give each `##` heading an explicit anchor, such as `{: #learning-goals }`. The "On this page" menu is built from these headings, so there is no separate list to keep in step.
4. Place runnable examples in `examples/week-02/` and include them inside Liquid `highlight java` blocks. Save each program's output next to it as `<Program>.expected.txt`, and include that file wherever the chapter shows the expected output.
5. Add `url: /lessons/week-02/` to the week's entry in `_data/weeks.yml` only when the chapter is ready.
6. Run `bash scripts/check-examples.sh`, then build and check the home page, chapter navigation, and downloads.

## Check the Java examples

Examples use JDK 21 or newer, without preview features or external libraries. From the repository root, run:

```shell
bash scripts/check-examples.sh
```

The script compiles each folder in `examples/` with `javac --release 21`, treating warnings as errors, and runs every program that has a `<Program>.expected.txt` file. The output must match that file exactly. The Pages workflow runs the same check on every push and pull request, so a lesson can never show output that the code no longer produces.

After an intentional change to a program, regenerate its expected output in its folder, for example:

```shell
javac *.java
java ExamScores > ExamScores.expected.txt
```

Generated `.class` files are ignored by Git. Lessons include the source and expected-output files with `include_relative`, so the displayed code and output match the downloadable examples.

Week 1 quotes numbers and output that depend on `ExamScores`, `ExamScoreChecks`, and `ShippingCost`: the walkthrough, the discussion notes, and the "Your turn" solutions. If any of these programs changes, work through the exercises again and update that text.
