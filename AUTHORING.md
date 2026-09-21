# Authoring guide

Conventions for writing lessons and examples on the course website. [README.md](README.md) covers running, structure, and publishing.

## Principles

- **No tool before its lecture.** Lessons and laboratory materials use only tools and notation introduced in their own or an earlier week.
- **Small steps over setup.** Favour small, understandable changes over large amounts of framework setup.
- **A familiar domain.** Programming examples use a familiar application domain (the first lesson uses room booking) so students can focus on engineering decisions.
- **One behaviour.** Working code, tests, diagrams, and documentation should describe the same behaviour.
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
3. Optionally rename lesson files, permalinks, and example folders so their `week-NN` names match the new numbers.
4. If the running example already has tags for weeks whose numbers changed, retag them to match: each lesson links to the tag named after its week number.
5. Build the site and check the home page, the About page, and each lesson's header and next-lesson link.

## Add a chapter

1. Create `week-02.md` using `week-01.md` as a structural example.
2. Set its front matter: `layout: lesson`, `title`, `description`, `permalink`, `key`, `category_label`, and `reading_time`. The header labels are optional: `language` (for example `Java`) and `format` (for example `Chapter + code walkthrough`). Optionally add `before_next_week`, one sentence on how to prepare, which the layout shows in the next-week box. Do not end the chapter with a paragraph about next week; that box already shows the next week's title and summary.
3. Give each `##` heading an explicit anchor, such as `{: #learning-goals }`. The "On this page" menu is built from these headings, so there is no separate list to keep in step.
4. Place runnable examples in `examples/week-02/` and include them inside Liquid `highlight java` blocks. Save each program's output next to it as `<Program>.expected.txt`, and include that file wherever the chapter shows the expected output.
5. Add `url: /lessons/week-02/` to the week's entry in `_data/weeks.yml` only when the chapter is ready.
6. Run `bash scripts/check-examples.sh`, then build and check the home page, chapter navigation, and downloads.

## Extend the running example

The course develops one application in the [room-booking repository](https://github.com/sangu-software-engineering/room-booking). Its `week-01` tag holds `examples/week-01/RoomCapacityDemo.java` as the first lesson presents it. For each later week:

1. Extend the application with that week's technique, and keep its README's run instructions current.
2. Commit, then tag the result with the week number, for example `git tag -a week-03 -m "End of week 3: Maven build and first tests"` and `git push origin week-03`.
3. Update the week's `example` line in `_data/weeks.yml` if the plan changed. The About page's week-by-week table and the lesson's "Running example" box show it, and the box links to the tag.
4. Push the tag before publishing the lesson, so the box's link works.

Lessons can quote the application, but runnable teaching programs still belong in `examples/week-NN/` with their expected output, where the check script covers them.

## Check the Java examples

Examples use JDK 21 or newer, without preview features or external libraries. From the repository root, run:

```shell
bash scripts/check-examples.sh
```

The script compiles each folder in `examples/` with `javac --release 21`, treating warnings as errors, and runs every program that has a `<Program>.expected.txt` file. The output must match that file exactly. The Pages workflow runs the same check on every push and pull request, so a lesson can never show output that the code no longer produces.

After an intentional change to a program, regenerate its expected output in its folder, for example:

```shell
javac *.java
java RoomCapacityDemo > RoomCapacityDemo.expected.txt
```

Generated `.class` files are ignored by Git. Lessons include the source and expected-output files with `include_relative`, so the displayed code and output match the downloadable examples.

The "Your turn" solutions in week 1 quote code and output that depend on `RoomCapacityDemo` and `RoomCapacityChecks`. If either file changes, work through the exercises again and update the solutions.
