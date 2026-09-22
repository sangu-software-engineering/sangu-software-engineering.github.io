---
layout: lesson
title: Software engineering foundations and clean code
description: Plan a pizza party in Java, then discover why software engineering is more than programming, and how clean code, small modules, refactoring, and honest metrics keep software easy to change.
permalink: /lessons/week-01/
key: foundations
category_label: Foundations
language: Java
format: Chapter + code walkthrough + exercises
reading_time: 40
before_next_week: "Bring the ability to run and explain a small program; Git knowledge is not assumed."
---

## Learning goals
{: #learning-goals }

By the end of this chapter, you should be able to:

- Explain how programming fits into software engineering and its lifecycle.
- Discuss scope, cost, time, and quality, and explain what makes a project hard to estimate.
- Compile, run, and explain a small Java program.
- Write clean code: meaningful names, small functions, useful comments, consistent conventions.
- Remove duplicated knowledge and split a big problem into small modules.
- Refactor code in small, checked steps.
- Calculate cyclomatic complexity, explain maintainability, and recognise code smells.
- Take responsibility for your work, including work done with AI help.

**Starting point:** you have already written programs in a high-level language. This chapter refreshes those skills in Java, with just enough syntax to follow the examples. Classes and object-oriented design get their own chapter in {% include week.html key="design" %}.

**Route through the chapter:** set up Java, meet a delicious problem, and build a small program for it. Then learn how professionals keep code like it clean, measurable, and easy to change, and finish by extending and cleaning up code yourself. Downloading files is enough for now: Git starts in {% include week.html key="git" %}.

## This course at a glance
{: #course-structure }

Over {{ site.data.weeks.size }} weeks, we take software from "someone has a problem" to "a working, maintainable application that runs somewhere real". Each week has a chapter like this one and a lab; lab instructions and assignments are in the SANGU LMS.

| Part | Topics |
| --- | --- |
| Foundations and tooling | Java refresher, clean code, Git, Maven |
| Understand and design | Requirements, planning, modular design |
| Verify and improve | Testing, debugging, refactoring, quality metrics, continuous integration |
| System and security | Architecture, secure construction |
| Deliver and operate | Docker, delivery pipelines, observability |

Today's clean-code ideas are seeds: they grow into design principles in {% include week.html key="design" %}, deliberate tests in {% include week.html key="testing" %}, and automated quality checks in {% include week.html key="quality" %}.

## Set up your Java toolkit
{: #java-toolkit }

Do this first, so installation problems show up now rather than halfway through the chapter. The examples use **Java 21 or newer**, with no preview features or external libraries. Install a full **JDK** (Java Development Kit), not only a runtime. An IDE is convenient, but a text editor and a terminal are enough.

- `javac` compiles a `.java` source file into `.class` bytecode.
- `java` starts the Java Virtual Machine (JVM) and runs the compiled program.

Follow the official [Java getting-started guide](https://dev.java/learn/getting-started/), then check that both commands work:

```shell
java --version
javac --version
```

We compile by hand so you can see what the compiler does. In {% include week.html key="build-tools" %}, Maven will automate compiling, testing, and packaging.

### Run a first program

Download [HelloCourse.java]({{ '/examples/week-01/HelloCourse.java' | relative_url }}) into a working folder:

{% highlight java %}
{% include_relative examples/week-01/HelloCourse.java %}
{% endhighlight %}

Open a terminal in that folder and run:

```shell
javac HelloCourse.java
java HelloCourse
```

Expected output:

{% highlight text %}
{% include_relative examples/week-01/HelloCourse.expected.txt %}
{% endhighlight %}

A public class and its file share a name: `HelloCourse` lives in `HelloCourse.java`. For now, read `public static void main(String[] args)` as "the program starts here". `static` lets us call methods without creating objects, which come later. `System.out.println` prints one line, and `+` joins text with other values.

> **Keep an eye on that `2`.** Two coffees per week? Per lecture? We will come back to it when we meet *magic numbers*.

### If the program does not run

| Symptom | Check first |
| --- | --- |
| `javac` is not recognised | Install a JDK and make sure its tools are available in your terminal. |
| The file cannot be found | Check the current folder and the exact filename, including `.java`. |
| The compiler reports a syntax error | Go to the reported line and look for missing braces, quotes, or semicolons. |
| Old output still appears | Save the file and compile again before running. |

A systematic debugging workflow follows in {% include week.html key="testing" %}.

## What are we engineering?
{: #engineering-software }

Your student club is throwing a party, and someone suggests a program that tells you how many pizzas to order. Easy: add up the slices, divide by eight, done. Then the questions start:

- Does Luka *really* eat five slices, or is he boasting?
- We need 11 slices. Do we order one pizza and a bit? (Pizzerias do not sell "a bit".)
- Is every pizza cut into 8 slices?
- What about vegetarians, the budget, and who actually places the order?

Only one of these questions is arithmetic. The rest are about people, rules, and responsibility.

**Software engineering** is the disciplined work of understanding, building, verifying, delivering, and evolving software under constraints. Programming turns the decisions into something a computer can run. Good engineering connects the code to the real problem *and* to evidence that it works.

This week's program answers one narrow question: **how many whole pizzas cover these appetites?** A real ordering system would also need menus, payments, and delivery tracking. So we describe the result honestly: the program *plans* an order; it does not *place* one.

### The lifecycle in one table
{: #lifecycle }

| Activity | In the pizza project |
| --- | --- |
| Understand the problem | Ask the club what goes wrong today: too little pizza? Too much? |
| Specify | Agree that we order whole pizzas and cover every slice someone wants. |
| Design | Keep "add up slices", "turn slices into pizzas", and "print results" separate. |
| Implement | Write the Java methods. |
| Verify | Check the tricky cases: 8 slices, 9 slices, nobody hungry. |
| Deliver | Share the program with instructions for running it. |
| Maintain | Update it when the pizzeria changes how it cuts pizzas. |

These activities are a vocabulary, not a conveyor belt: a change request sends you straight back to specifying. Keep two words apart. **Verification** asks "did we build the thing right?", that is, does the code follow our rules? **Validation** asks "did we build the right thing?", that is, do the rules solve the real problem? A perfect pizza count still fails validation if half the guests are vegan and nobody asked. Processes that organise these activities come in {% include week.html key="process" %}.

## Cost, time, quality, and estimates
{: #constraints }

The dean loves your planner: "Let's use it for the freshers' party. On Friday. Oh, and add vegetarian options, bill splitting, and a live delivery map."

Every project balances four concerns:

| Concern | Question | Pizza example |
| --- | --- | --- |
| Scope | What exactly are we building? | Only the planner, or payments and maps too? |
| Time | When is it needed? | Friday, 18:00, before the guests arrive. |
| Cost | What effort and money are available? | Two students, a few evenings, no budget. |
| Quality | What must be true of the result? | Never under-order; clear output; easy to change. |

They pull against each other. With a fixed deadline and team, extra features must come from somewhere, and quietly lowering quality is the usual "somewhere". The honest options are to **reduce scope** (the planner now, the map later), **move the deadline**, or **add resources**. Even the last one has a catch: Fred Brooks observed that adding people to a late software project tends to make it later, because newcomers need training and everyone needs more coordination. Nine cooks cannot bake one pizza in one minute. Engineers sum it up with an old joke: "fast, cheap, good: pick two".

### Estimating a project

Before you promise "Friday", you need an estimate. It is built from a few characteristics:

- **Size:** how much there is to build, counted in lines of code (easy to count, but it rewards long-winded code), function points (the functionality users see: inputs, outputs, queries, stored data), or story points (a team's relative sizes; see {% include week.html key="process" %}).
- **Effort:** person-hours of work.
- **Duration:** calendar time. 40 hours of effort is not "done tomorrow" just because five people are free.
- **Cost:** mostly effort multiplied by the cost of people's time, plus tools and servers.
- **Uncertainty:** early in a project, estimates can easily be off several times over, in either direction. They narrow as requirements become clear, a pattern Steve McConnell calls the *cone of uncertainty*.

### Change is normal

Requirements change: a new pizzeria, a new slice count, a new idea from the dean. The cost of a change depends mostly on **how many places in the code know about the thing that changed**. If "8 slices per pizza" lives in one named constant, a new pizzeria costs one line. If the number `8` is scattered across fourteen files, it costs fourteen edits and at least one forgotten one. That is why the rest of this chapter cares so much about clean, non-duplicated, modular code: it keeps change cheap.

Quality itself has several faces: correctness, usability, maintainability, reliability, and security and privacy. Which matters most depends on the context. For our planner, correctness and maintainability come first.

## Programming foundations in Java
{: #programming-foundations }

With the problem framed, we can express the rule in Java. You have used these building blocks before; here is their Java form.

### Values and variables

Every Java variable has a type. `int` holds a whole number, `boolean` holds `true` or `false`, and `String` holds text:

```java
int slicesWanted = 11;
boolean fitsInTwoPizzas = slicesWanted <= 16;
String favouriteTopping = "margherita";
```

`=` stores a value; `==` compares two primitive values such as `int`s.

### Integer division: the pizza trap

Dividing one `int` by another gives an `int`: the remainder is thrown away. The `%` operator gives you that remainder.

```java
int fullPizzas = 11 / 8;  // 1, not 1.375
int extraSlices = 11 % 8; // 3 slices that still need a pizza
```

Remember this. It is about to cause trouble.

### Methods, conditionals, arrays, and loops

A method names a computation, receives inputs through parameters, and may return a result. In `countPizzasNeeded(int slicesWanted)`, `slicesWanted` is the parameter; in the call `countPizzasNeeded(11)`, 11 is the argument. A method that *returns* its answer instead of printing it can be reused anywhere: in a message, a calculation, or a check.

`if` decides whether a block runs, and `for` repeats one. An *array* is a fixed-length sequence of values of one type; the enhanced `for` loop visits each value in order:

```java
int[] appetites = {3, 2, 5, 1};
int totalSlices = 0;
for (int slices : appetites) {
    totalSlices += slices;
}
if (totalSlices > 16) {
    System.out.println("That's a lot of pizza.");
}
```

An empty array makes the loop run zero times, so the total stays 0.

## A complete Java example
{: #walkthrough }

Download [PizzaPartyPlanner.java]({{ '/examples/week-01/PizzaPartyPlanner.java' | relative_url }}). Four guests want 3, 2, 5, and 1 slices, and each pizza has 8 slices.

{% highlight java %}
{% include_relative examples/week-01/PizzaPartyPlanner.java %}
{% endhighlight %}

Compile and run it:

```shell
javac PizzaPartyPlanner.java
java PizzaPartyPlanner
```

Expected output:

{% highlight text %}
{% include_relative examples/week-01/PizzaPartyPlanner.expected.txt %}
{% endhighlight %}

### Trace the result

`sumSlices` adds 3, 2, 5, and 1: 11 slices. In `countPizzasNeeded`, `11 / 8` is 1 with a remainder of 3, so the second `if` adds one more pizza: **2 pizzas**. They have 16 slices, so 5 are left over, and breakfast is sorted. For an empty party, `0 / 8` is 0 with no remainder: no pizza, no bill.

Notice how the work is divided:

- `sumSlices` adds up the appetites.
- `countPizzasNeeded` turns slices into whole pizzas. It is the only place that knows the rounding rule.
- `countLeftoverSlices` *asks* `countPizzasNeeded` instead of repeating its logic.
- `main` supplies the data and prints the results. No other method prints anything.

`SLICES_PER_PIZZA` is a named constant: `final` means it can never be reassigned, and its name explains the `8`. A negative slice count makes no sense, so `countPizzasNeeded` refuses it with `throw`, which stops the program with an error message instead of quietly producing nonsense. Error handling gets proper treatment later; for now, read `throw` as "stop and complain loudly".

## Checking behaviour before claiming success
{: #checking-behaviour }

The compiler checks grammar, not meaning. This version compiles perfectly:

```java
static int countPizzasNeeded(int slicesWanted) {
    return slicesWanted / SLICES_PER_PIZZA;
}
```

For our party, it orders 1 pizza for 11 slices. Three guests go hungry, and the program does not even blink. This is a **logic error**: the code runs, but it breaks our rule.

Decide the expected results *from the rule*, before running the program. Otherwise, it is far too easy to explain a wrong answer away.

| Slices wanted | Expected pizzas | Why |
| --- | --- | --- |
| 0 | 0 | Nobody is hungry. |
| 1 | 1 | Even one slice needs a whole pizza. |
| 8 | 1 | Exactly one pizza. |
| 9 | 2 | One slice over: a second pizza. |
| 16 | 2 | Exactly two pizzas. |
| 17 | 3 | One slice over again. |
| -1 | rejected | A negative appetite is a mistake. |

The buggy version gets only 1, 9, and 17 wrong, where a pizza is "partly needed". Rows 0, 8, and 16 give the right answer under both versions, so on their own they prove nothing. Good checks sit on **boundaries**, where a plausible mistake produces a different answer. The exercises in [Your turn](#your-turn) make these checks runnable. Automated tests arrive in {% include week.html key="build-tools" %}, and deliberate test design in {% include week.html key="testing" %}.

## What is clean code?
{: #clean-code }

Our planner works. Is that enough? Ask anyone who has opened a six-month-old project, thought "who wrote this mess?", checked, and found their own name.

**Clean code** is code that other people, including future you, can read, understand, and change safely. In *Clean Code*, Robert C. Martin collects definitions from well-known programmers: Bjarne Stroustrup, the creator of C++, says clean code "does one thing well"; Grady Booch compares it to well-written prose; Ron Jeffries adds that it passes its tests and contains no duplication.

Why bother, if the computer does not care?

- **Code is read far more often than it is written.** Martin puts the ratio at well over 10 to 1, because every new line starts with reading the old ones.
- **Mess slows everyone down.** Teams that race through the first months of a project start to crawl when every change breaks two other things. Adding people makes it worse: newcomers do not know the design, so they add more mess.
- **Mess attracts mess.** In the "broken windows" metaphor from *The Pragmatic Programmer*, one broken window makes a building look abandoned, and soon more windows break. One ugly hack invites the next.
- **"Later" rarely comes.** Martin quotes LeBlanc's law: *later equals never*.

Two habits follow:

> **The Boy Scout rule:** leave the code a little cleaner than you found it. Rename one confusing variable, split one long method, remove one duplicate. If everyone does this every time, the code cannot rot.

> **You are an author.** Your readers are your teammates, your reviewers, and you in six months. Write for them.

Clean code is not a beauty contest. It is an economic argument: it keeps the cost of the *next* change low, and in software there is always a next change.

## Code conventions
{: #conventions }

A **code convention**, or style guide, is a set of agreed rules for how code looks: names, braces, spacing, line length. Conventions do not make code correct; they make every file feel familiar, so readers spend their energy on the logic instead of on someone's personal style. A widely used convention for Java is the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html). Some highlights:

| Rule | Example |
| --- | --- |
| Class names are `UpperCamelCase` nouns, and the file has the same name. | `PizzaPartyPlanner` in `PizzaPartyPlanner.java` |
| Method names are `lowerCamelCase`, usually verbs. | `countPizzasNeeded`, `sendMessage` |
| Variables and parameters are `lowerCamelCase`. | `slicesWanted`, not `slices_wanted` or `sw` |
| Constants (`static final`, never changing) are `UPPER_SNAKE_CASE`. | `SLICES_PER_PIZZA` |
| Acronyms are written like ordinary words. | `XmlHttpRequest`, `customerId` (not `customerID`) |
| `if`, `else`, `for`, and `while` always use braces, even around one statement. | Never `if (isDone) stop();` |
| One statement per line and one variable per declaration. | Not `int guests, slices;` |
| Lines are at most 100 characters long. | Wrap long lines, or better, extract a method. |
| Local variables are declared close to their first use. | Not all piled up at the top of a method. |

> **Conventions are team decisions.** Google indents with 2 spaces; this course's examples use 4, a common IDE default. *Clean Code* recommends wildcard imports such as `import java.util.*;`, while Google forbids them. Neither side is wrong. What *is* wrong is mixing styles in one project. A team picks one guide and lets a formatter apply it automatically, so nobody argues about spaces in code review.

Layout carries meaning, too. *Clean Code* suggests the **newspaper metaphor**: the big picture at the top, details further down, a blank line between ideas, and related lines kept close together.

## Clean code in practice
{: #readable-code }

### Meaningful names

A good name tells you why something exists, what it does, and how it is used. If a name needs a comment to explain it, the name is not finished yet.

```java
// Before: the comment is doing the name's job
int d; // days since the cat was last fed

// After: the name does its own job
int daysSinceLastMeal;
```

A few rules of thumb from *Clean Code*:

- **Reveal intent.** `guests * slicesPerGuest` beats `a * b`, and `isGameOver` beats `flag`.
- **Do not lie.** A method called `isAvailable` that only counts seats misleads everyone who calls it.
- **Make differences meaningful.** `song1` and `song2`, or `data` and `info`, say nothing about how they differ. Try `currentSong` and `nextSong`.
- **Use pronounceable, searchable names.** Try saying `lstUpdTmstmp` out loud in a code review. And searching for `7` finds every 7 in the project, while `MAX_LIVES` finds exactly what you want.

**Magic numbers** deserve a special mention. Remember `lectureWeeks * 2` in `HelloCourse`? Two *what*? A raw number whose meaning is not obvious is a *magic number*. Give it a name:

```java
static final int COFFEES_PER_WEEK = 2;

int coffeesNeeded = lectureWeeks * COFFEES_PER_WEEK;
```

Some numbers explain themselves in context: `radius * 2` for a diameter does not need a constant called `TWO`.

### Functions

- **Small.** Martin's first rule of functions is that they should be small; his second is that they should be smaller than that.
- **One thing.** If you can only describe a function with "and", as in `validateAndSaveAndEmailOrder`, it is several functions sharing one name.
- **Few parameters.** Zero, one, or two are easy to follow; three need a good reason. `createCharacter("Nino", 12, 8, 3, true, false, "red")` is a guessing game.
- **No flag arguments.** `printMenu(true)` announces that the function does two different things. Write `printFullMenu()` and `printVegetarianMenu()` instead.
- **No hidden side effects.** A method called `isPizzaReady()` that also charges your card is a trap.
- **Calculation separate from presentation.** `countPizzasNeeded` returns a number and prints nothing, so every caller can reuse it.

### Comments

A comment should explain what the code *cannot* say. Before writing one, try to improve the code so the comment becomes unnecessary.

```java
// Bad: repeats the code
count++; // increase count by one

// Bad: explains unclear code...
// check whether the user can still skip
if (!p && s >= 6) { ... }

// ...so fix the code instead
if (canSkip(isPremium, skipsThisHour)) { ... }

// Good: explains WHY, which the code cannot express
// The pizzeria sells whole pizzas only, so any leftover slice means one more pizza.
```

Good comments explain intent and reasons, warn about consequences, or mark agreed temporary work with a searchable `TODO` that links to an issue. Bad comments repeat the code, turn into lies when the code changes, or keep **commented-out code** alive "just in case". Delete it: version control remembers it for you ({% include week.html key="git" %}).

## Don't repeat yourself
{: #duplication }

When one rule lives in two places, sooner or later someone updates one copy and forgets the other. That is why the **DRY** principle, *don't repeat yourself*, is one of the most important in software.

A music app lets free users skip 6 songs per hour, and the rule appears twice:

```java
static String skipButtonLabel(boolean isPremium, int skipsThisHour) {
    if (!isPremium && skipsThisHour >= 6) {
        return "Skip (locked)";
    }
    return "Skip";
}

static void onSkipPressed(boolean isPremium, int skipsThisHour) {
    if (!isPremium && skipsThisHour >= 6) {
        System.out.println("No skips left! Please enjoy this ad about socks.");
    } else {
        System.out.println("Skipping to the next song...");
    }
}
```

The company lowers the limit to 5. A developer updates `onSkipPressed` and misses the button. Now, on the sixth skip, the button cheerfully says "Skip", and pressing it plays the sock ad. Users are furious, and the socks still do not sell.

The cure is to give the rule **one home** and let everyone ask it:

```java
static final int FREE_SKIPS_PER_HOUR = 6;

static boolean canSkip(boolean isPremium, int skipsThisHour) {
    return isPremium || skipsThisHour < FREE_SKIPS_PER_HOUR;
}

static String skipButtonLabel(boolean isPremium, int skipsThisHour) {
    if (canSkip(isPremium, skipsThisHour)) {
        return "Skip";
    }
    return "Skip (locked)";
}
```

`onSkipPressed` asks `canSkip` in the same way. Changing the limit is now one edit, and the condition has a name that explains it. The positive wording helps, too: `canSkip(...)` reads more easily than `!cannotSkip(...)`.

### Duplication or coincidence?

Not every pair of look-alike lines is duplication:

```java
static boolean canVote(int age) {
    return age >= 18;
}

static boolean paysAdultTicket(int age) {
    return age >= 18;
}
```

The code is identical, but the rules are not: one comes from election law, the other from a cinema's price list. If the cinema decides that adult prices start at 16, voting must not change. Ask: **if one changes, must the other change too?** If yes, it is duplication, so give it one home. If no, it is a coincidence, so leave it alone. When you are unsure, use the **rule of three**: the first time, just write it; the second time, notice the repetition; the third time, extract it.

## Big project, small modules
{: #modules }

Nobody eats a whole pizza in one bite, and nobody builds a large system in one file. Here is the dean's dream app, fully grown:

```text
pizza-party-app
├── accounts        who is ordering
├── menu            pizzas, sizes, prices
├── planner         appetites → pizzas   (this week's code)
├── bill-splitter   who owes how much
├── payments        talks to the bank
├── delivery        "where is my pizza?"
└── notifications   "your pizza is 2 minutes away!"
```

Each **module** has one clear job and a small, well-defined way for other parts to use it. The planner offers `countPizzasNeeded`; the bill splitter does not need to know *how* it rounds. Good modules have two properties that you will study properly in {% include week.html key="design" %}:

- **High cohesion:** everything inside a module belongs together. Prices live in `menu`, not scattered across `payments` and `delivery`.
- **Low coupling:** modules know as little as possible about each other's insides. If `delivery` switches map providers, `menu` should not notice.

Splitting pays off immediately. You can think about one small piece at a time; different people can build different modules at once; and small pieces are far easier to estimate. "How long will the whole app take?" is a wild guess, but "how long will the bill splitter take?" has an answer.

To find module boundaries, ask **what changes for different reasons**. Prices change when the pizzeria says so; the map changes when the map provider does. The same idea works at every scale: a system splits into modules, modules into classes, and classes into methods. `PizzaPartyPlanner` already does it at the smallest scale: four methods, four jobs.

## Refactoring
{: #refactoring }

**Refactoring** means improving the internal structure of code *without changing what it does*. Users see no difference; the next developer sees a huge one. Martin Fowler's book *Refactoring* made the practice famous and catalogues dozens of named refactorings.

Why not rewrite everything from scratch? Because it is very hard to make a brand-new version behave exactly like the old one, and the old one keeps changing in the meantime; *Clean Code* tells of a "grand redesign" race that lasted ten years. Refactor in small steps instead:

1. **Have checks** that pin down the current behaviour.
2. **Make one small change:** rename, extract, or simplify.
3. **Run the checks.** If they pass, continue. If they fail, undo the last step; it was small, so undoing is cheap.
4. **Repeat** until the code is clean enough for the next task.

Two more rules: **do not mix refactoring with new features**, so that when something breaks you know why; and **first make it work, then make it right**.

| Refactoring | What it does |
| --- | --- |
| Rename | Gives a variable, method, or class a name that tells the truth. |
| Extract method | Moves a chunk of code into a method whose name explains it. |
| Replace magic number with constant | Turns `8` into `SLICES_PER_PIZZA`. |
| Remove flag argument | Splits `printMenu(true)` into two clearly named methods. |
| Replace nested conditional with guard clauses | Returns early for special cases instead of nesting `if`s. |
| Remove dead code | Deletes code that is never used or is commented out. |

Your IDE can do many of these safely. In IntelliJ IDEA, for example, the **Refactor** menu offers *Rename* (Shift+F6) and *Extract Method* (Ctrl+Alt+M on Windows and Linux), and both update every usage for you.

### A refactoring, step by step

A video game calculates the hero's health after a hit. It works, and it is awful:

```java
// calc
static int calc(int h, int d, boolean f) {
    int r;
    if (f == true) {
        r = h - d * 2;
    } else {
        r = h - d;
    }
    if (r < 0) {
        r = 0;
    }
    return r;
}

// ...three files away:
health = calc(health, 7, true); // true?! true what?
```

First, checks that pin down today's behaviour, decided before touching anything:

| Health | Damage | Critical hit? | Expected health |
| --- | --- | --- | --- |
| 20 | 7 | no | 13 |
| 20 | 7 | yes | 6 |
| 5 | 7 | no | 0 (never below zero) |
| 5 | 7 | yes | 0 |

Then, one small step at a time, running the checks after each:

1. **Rename** `calc`, `h`, `d`, and `f` to `healthAfterHit`, `health`, `damage`, and `isCriticalHit`, and delete the useless `// calc` comment.
2. **Replace the magic number** `2` with `CRITICAL_HIT_MULTIPLIER`.
3. **Simplify:** `isCriticalHit == true` is just `isCriticalHit`, and "subtract, but never below zero" is exactly `Math.max(0, ...)`.
4. **Remove the flag argument:** instead of telling the method *how* to calculate damage, callers pass the actual damage.

```java
static final int CRITICAL_HIT_MULTIPLIER = 2;
static final int SWORD_DAMAGE = 7;

static int criticalDamage(int baseDamage) {
    return baseDamage * CRITICAL_HIT_MULTIPLIER;
}

static int healthAfterHit(int health, int damage) {
    return Math.max(0, health - damage); // health never drops below zero
}

// ...and the call now reads like a sentence:
health = healthAfterHit(health, criticalDamage(SWORD_DAMAGE));
```

The checks give the same answers as before, but every name now tells the truth and each method does one thing. The next section measures how much simpler it became.

## Measuring code quality
{: #quality-metrics }

"This code feels messy" is a start, but engineers also want numbers they can compare and track. Treat every metric as a **smoke alarm, not a judge**: it tells you where to look, not what to conclude. Tools that calculate these metrics automatically arrive in {% include week.html key="quality" %}.

### Cyclomatic complexity

**Cyclomatic complexity** (CC), introduced by Thomas McCabe in 1976, counts the independent paths through a piece of code. For one method:

> **CC = number of decision points + 1**

In Java, the decision points are `if`, `for`, `while`, `do`-`while`, each `case` of a `switch`, `catch`, the conditional operator `?:`, and every `&&` or `||` in a condition. A plain `else` does not count: it is the other side of an `if` you have already counted. Tools differ slightly on `&&`, `||`, and `switch`, so compare numbers from the same tool.

Count the decision points in this aquapark ticket price:

```java
static final int ADULT_PRICE = 30;       // GEL
static final int WEEKEND_SURCHARGE = 5;  // GEL

static int ticketPrice(int age, boolean isStudent, boolean isWeekend) {
    if (age < 3) {                  // +1
        return 0;                   // babies swim for free
    }
    int price = ADULT_PRICE;
    if (age < 12 || isStudent) {    // +1 for the if, +1 for ||
        price = price / 2;
    }
    if (isWeekend) {                // +1
        price += WEEKEND_SURCHARGE;
    }
    return price;
}
```

Four decision points give **CC = 5**. Each path is a scenario the reader must keep in mind, and it takes 5 test cases to walk every independent path: a baby, a child, a student, an adult on a weekday, and an adult at the weekend.

| Method | Decision points | CC |
| --- | --- | --- |
| `sumSlices` | one `for` | 2 |
| `countPizzasNeeded` | two `if`s | 3 |
| the game's old `calc` | two `if`s | 3 |
| the new `healthAfterHit` | none | 1 |

A common rule of thumb: 1–10 is simple; 11–20 is getting complex; above 20, most teams refactor; above 50, a method is practically untestable. McCabe suggested 10 as a sensible limit, and many tools warn above 10 by default.

> **Know your library.** Since Java 18, `Math.ceilDiv(slicesWanted, SLICES_PER_PIZZA)` rounds up for you. Using it in `countPizzasNeeded` would remove one `if` and lower its CC to 2. Replacing hand-made logic with a well-known library method is a refactoring, too.

CC has a blind spot: it counts paths, not how hard they are to *read*. Ten `if`s nested inside each other have the same CC as ten `if`s in a row, yet the nested version is far harder to follow. That is why tools such as SonarQube also report *cognitive complexity*, which penalises nesting.

### Maintainability

**Maintainability** is how easily developers can understand, fix, and change software. The ISO/IEC 25010 quality model splits it into modularity, reusability, analysability (can I understand it?), modifiability (can I change it safely?), and testability (can I check it?). Everything in this chapter so far is about maintainability.

Some tools squeeze it into one number, the **Maintainability Index** (MI), proposed by Paul Oman and Jack Hagemeister in 1992. Visual Studio uses this version:

```text
MI = max(0, (171 - 5.2 * ln(HV) - 0.23 * CC - 16.2 * ln(LOC)) * 100 / 171)
```

HV is the *Halstead volume*, which grows with the number of operators and operands in the code; CC is the cyclomatic complexity; LOC is the number of lines of code. The result runs from 0 to 100, higher is better, and Visual Studio shows 20–100 as green, 10–19 as yellow, and 0–9 as red.

You do not need to memorise the formula, only its message: **size, branching, and a large vocabulary all make code harder to maintain.** Know its limits, too: its coefficients were fitted to industrial code from the early 1990s, and it knows nothing about names or tests, so a method full of variables called `x1` can still score well. Use it to spot trends and hot spots, never as a grade.

### Code smells

A **code smell** is a surface hint that something deeper might be wrong. Kent Beck coined the term, Martin Fowler's *Refactoring* made it famous, and chapter 17 of *Clean Code* lists dozens more. A smell is not a bug and not proof; it is a reason to look closer, and most smells have a matching refactoring.

| Smell | How it smells | Usual fix |
| --- | --- | --- |
| Mysterious name | `int d;`, `String s2;`, `void doStuff()` | Rename |
| Long method | A 200-line `main` that reads input, calculates, prints, and makes coffee | Extract method |
| Duplicated code | The same skip rule in two places | Extract a method or constant |
| Magic number | `if (level > 42)`: why 42? | Named constant |
| Long parameter list | `createCharacter("Nino", 12, 8, 3, true, false, "red")` | Fewer parameters; group related values |
| Flag argument | `printMenu(true)` | Split into two methods |
| Deep nesting ("arrow code") | `if` inside `if` inside `for` inside `if`, drifting right like an arrowhead | Guard clauses, extract method |
| Comment as deodorant | A long comment explaining confusing code | Make the code clear, then delete the comment |
| Dead code | Unused methods, `// oldCalc(x);` | Delete it; Git remembers |
| Large class ("God class") | An `EverythingManager` with 3,000 lines | Split into modules |
| Shotgun surgery | One small change needs edits in many files | Gather that knowledge in one place |

To sniff them out, **read the code aloud**: if you stumble or have to explain a name, it smells. Notice the **urge to comment**, which often means the code wants a better name or its own method. **Count indentation levels**: more than two or three in one method is a warning sign. **Notice fear**: code you are scared to change is probably tangled or unchecked. And ask for **code review**, which starts in {% include week.html key="git" %}: a colleague's nose is the best smell detector.

## Professional responsibility
{: #responsibility }

Software decides how much pizza arrives, but also who gets a loan, which medicine a patient receives, and whether a car brakes in time. That is why software engineering has a code of ethics. The [Software Engineering Code of Ethics and Professional Practice](https://www.acm.org/code-of-ethics/software-engineering-code) of the ACM and the IEEE Computer Society has eight principles. In short, software engineers should:

1. **Public:** act in the public interest.
2. **Client and employer:** serve their clients and employers well, consistent with the public interest.
3. **Product:** make their products meet the highest professional standards possible.
4. **Judgment:** keep their professional judgment honest and independent.
5. **Management:** lead and manage software work ethically.
6. **Profession:** protect the integrity and reputation of the profession.
7. **Colleagues:** be fair to and supportive of their colleagues.
8. **Self:** keep learning throughout their careers.

What does this mean in everyday work, even on a student project?

- **Be honest about what your software does.** Our program *plans* an order; printing "Pizza ordered!" would be a lie. Report bugs and limitations clearly, with the input and the observed result, so others can reproduce them.
- **Defend the code, and do not promise the impossible.** *Clean Code* compares programmers to surgeons: if a patient demanded that the surgeon skip hand-washing to save time, a professional would refuse, because they understand the risk better. When scope grows and the deadline does not, say so early, calmly, and with facts.
- **Collect only the data you need.** The planner needs slice counts, not phone numbers. If a future version asks about allergies, that is health information: decide why it is needed, who can see it, and how long it is kept *before* collecting it. Use made-up data while learning.
- **Use AI assistance with accountability.** Where the course rules allow AI tools, you still remain responsible for what you submit. An AI that confidently writes `slicesWanted / SLICES_PER_PIZZA` has still left three guests hungry. Read, run, and check everything; record substantial assistance; never upload private data, credentials, or someone else's unpublished work; and credit other people's work.
- **Never claim a check you did not run.** "It works on my machine" is a starting point, not evidence.

## Check your understanding
{: #check-understanding }

Try answering before opening the discussion notes.

<details markdown="1">
<summary>Why is a correct pizza count not a complete party-ordering system?</summary>

It only plans quantities. It does not choose toppings, check the budget, place or pay for an order, or confirm the delivery. Those need more requirements and more code, and until they exist, the program's messages must not pretend otherwise.

</details>

<details markdown="1">
<summary>An AI assistant "fixes" the rounding bug by adding 1 to the division. Which rows of the expected-results table catch it?</summary>

With `return slicesWanted / SLICES_PER_PIZZA + 1;`, the rows for 0, 8, and 16 fail: it orders a pizza for an empty party and an extra pizza for every exact multiple of 8. It fails exactly the rows that the truncating bug passes. Checks on *both* sides of each boundary catch both mistakes.

</details>

<details markdown="1">
<summary>The dean wants three more features by the same Friday. What do you do?</summary>

Explain the effect on effort, risk, and quality, and offer real choices: a smaller scope now and the rest later, a later deadline, or more people (knowing that newcomers need time to become productive). Agree on priorities openly instead of silently cutting quality.

</details>

<details markdown="1">
<summary>Two methods both contain age >= 18. Should you merge them?</summary>

Only if they express the same rule. If changing one must always change the other, it is duplication: give the rule one home. If they come from different rules, such as a voting age and a ticket price, it is a coincidence, and merging them would couple unrelated decisions.

</details>

<details markdown="1">
<summary>A method has a cyclomatic complexity of 14. Is it bad code?</summary>

Not necessarily, but it deserves a look. It has 14 independent paths to understand and needs 14 test cases to walk them all. Check whether it does more than one thing, whether its conditions could get names, and whether deep nesting could become guard clauses. The number is a smoke alarm, not a verdict.

</details>

<details markdown="1">
<summary>What does the planner do when the appetites are 5 and -3? Is that acceptable?</summary>

`sumSlices` returns 2, and `countPizzasNeeded(2)` happily returns 1 pizza. A negative appetite is nonsense, but only a negative *total* is rejected, so this mistake slips through. The check belongs where individual appetites are handled: `sumSlices` should reject any negative value. Deciding where a rule lives is a design decision.

</details>

## Your turn
{: #your-turn }

These exercises build on each other, so do them in order, in the folder that holds `PizzaPartyPlanner.java`. In each one, decide the expected results **before** you run anything. A solution is hidden under each exercise; try the exercise before opening it.

### Make the checks runnable

Comparing output with the table by eye is slow and easy to skip. Download [PizzaChecks.java]({{ '/examples/week-01/PizzaChecks.java' | relative_url }}) into the same folder. It calls the real `countPizzasNeeded` for each case and prints PASS or FAIL:

{% highlight java %}
{% include_relative examples/week-01/PizzaChecks.java %}
{% endhighlight %}

Compile both files together, then run the checks:

```shell
javac PizzaPartyPlanner.java PizzaChecks.java
java PizzaChecks
```

Expected output:

{% highlight text %}
{% include_relative examples/week-01/PizzaChecks.expected.txt %}
{% endhighlight %}

1. Add a `check` call for each remaining valid row of the [expected-results table](#checking-behaviour). Compile and run again: every line should say PASS.
2. In `countPizzasNeeded`, replace everything from `int pizzas` to `return pizzas;` with the buggy `return slicesWanted / SLICES_PER_PIZZA;`. Which checks fail, and why exactly those?
3. Now try the AI's version, `return slicesWanted / SLICES_PER_PIZZA + 1;`. Which checks fail this time?
4. Restore the original code before moving on.

A program that compares results with expectations is the basic idea behind automated testing. In {% include week.html key="build-tools" %}, these checks become JUnit tests that Maven runs; JUnit also makes it easy to check the "rejected" row, where the method throws instead of returning.

<details markdown="1">
<summary>Check your results</summary>

The four remaining valid rows:

```java
check(0, 0);
check(1, 1);
check(16, 2);
check(17, 3);
```

With the truncating version, three checks fail:

```text
FAIL: countPizzasNeeded(9) returned 1, expected 2
FAIL: countPizzasNeeded(1) returned 0, expected 1
FAIL: countPizzasNeeded(17) returned 2, expected 3
```

These are exactly the amounts with a remainder, which the division throws away. With the AI's version, the *other* three fail: 8, 0, and 16. Each bug hides from half of the checks, and together the checks catch both. A check earns its place when a plausible mistake can make it fail.

</details>

### Six-slice pizzas

A change request arrives: the club switches to a pizzeria that cuts every pizza into **6 slices**.

1. Update the expected results in `PizzaChecks` *before* touching the planner. Which existing expectations change? Add checks just inside and just outside the new boundaries.
2. Run the checks against the unchanged planner. The updated expectations should fail, which proves that the checks now describe the new behaviour.
3. Change the planner and run the checks until every line passes. How many lines of the planner did you change? Then run `PizzaPartyPlanner` and explain what changed in its output.

<details markdown="1">
<summary>One possible solution</summary>

The updated checks:

```java
check(8, 2);   // changed: was 1
check(9, 2);
check(0, 0);
check(1, 1);
check(16, 3);  // changed: was 2
check(17, 3);
check(6, 1);   // new boundary: exactly one pizza
check(7, 2);   // one slice over
check(12, 2);  // exactly two pizzas
check(13, 3);  // one slice over
```

Against the old planner, the checks for 8, 16, 7, and 13 fail. The planner needs exactly one changed line:

```java
static final int SLICES_PER_PIZZA = 6;
```

Every check now passes. `PizzaPartyPlanner` still orders 2 pizzas, but they have 12 slices instead of 16, so `Leftover slices` drops from 5 to 1. The rule changed in one line because the slice count lived in exactly one named place. The checks changed in several places, and that is normal: they describe behaviour, and the behaviour changed.

</details>

### Smell hunt at the cat café

Download [CatMood.java]({{ '/examples/week-01/CatMood.java' | relative_url }}). Its parameters are the hours since the cat's last meal, the naps it has had today, and whether it is sunny. It works, but it smells:

{% highlight java %}
{% include_relative examples/week-01/CatMood.java %}
{% endhighlight %}

Expected output:

{% highlight text %}
{% include_relative examples/week-01/CatMood.expected.txt %}
{% endhighlight %}

1. List every smell you can find. There are at least six.
2. Calculate the cyclomatic complexity of `mood`.
3. Before changing anything, write `CatMoodChecks.java` with at least four checks. Hint: the interesting boundaries are 5 versus 6 hours since the last meal, and 2 versus 3 naps. Compare strings with `equals`, not `==`.
4. Refactor in small steps, running your checks after each one. Then calculate the cyclomatic complexity again. Did it change? What *did* change?

<details markdown="1">
<summary>One possible solution</summary>

The smells: mysterious names (`h`, `n`, `s`, `r`); magic numbers (`5` and `3`); a comment that says nothing; `s == true` instead of just `s`; three levels of nested `if`/`else`; commented-out code; and a result variable that starts as a meaningless `""` and is reassigned in every branch. The cyclomatic complexity is 4: three `if`s plus one.

The checks, shown after the method was renamed (your IDE's *Rename* updates them, too):

```java
public class CatMoodChecks {
    static void check(int hoursSinceMeal, int napsToday, boolean isSunny, String expected) {
        String actual = CatMood.describeMood(hoursSinceMeal, napsToday, isSunny);
        String status = "FAIL";
        if (actual.equals(expected)) {
            status = "PASS";
        }
        System.out.println(status + ": describeMood(" + hoursSinceMeal + ", " + napsToday + ", "
                + isSunny + ") returned " + actual + ", expected " + expected);
    }

    public static void main(String[] args) {
        check(6, 4, true, "hangry");      // hunger beats sunshine
        check(5, 4, true, "sunbathing");  // exactly 5 hours is not hangry yet
        check(5, 2, false, "zoomies");    // 2 naps are not enough
        check(5, 3, false, "purring");    // 3 naps: calm cat
    }
}
```

The cleaned-up method, with `main` now calling `describeMood`:

```java
static final int HANGRY_AFTER_HOURS = 5;
static final int NAPS_NEEDED_TO_CALM_DOWN = 3;

static String describeMood(int hoursSinceMeal, int napsToday, boolean isSunny) {
    if (hoursSinceMeal > HANGRY_AFTER_HOURS) {
        return "hangry";
    }
    if (isSunny) {
        return "sunbathing";
    }
    if (napsToday < NAPS_NEEDED_TO_CALM_DOWN) {
        return "zoomies";
    }
    return "purring";
}
```

All checks pass, and the program prints the same four moods. The cyclomatic complexity is *still* 4: the cat genuinely has four moods, and those decisions are real rules. Everything else improved: the names explain themselves, the numbers have names, the nesting went from three levels to one, and the rules read from top to bottom in order of priority. Cognitive complexity notices this improvement; cyclomatic complexity does not.

</details>

## Summary and key terms
{: #summary }

- Software engineering is programming plus understanding, verifying, delivering, and evolving software under constraints. Scope, time, cost, and quality pull against each other, so discuss trade-offs openly.
- Estimates rest on size, effort, duration, cost, and uncertainty. Change is normal, and its cost depends on how many places know about the changed thing.
- Clean code is easy to read, understand, and change: truthful names, small single-purpose functions, comments that explain *why*, and the team's conventions applied by a formatter.
- Give each rule one home, but do not confuse coincidence with duplication. Split big systems into cohesive, loosely coupled modules.
- Refactor in small, checked steps, never mixed with new features, and leave code cleaner than you found it.
- CC = decision points + 1, with 10 as a common limit. The Maintainability Index shows trends. Smells are hints, not verdicts. You are accountable for what you deliver.

| English | ქართული |
| --- | --- |
| Cost, timeliness, quality | ღირებულება, დროულობა, ხარისხი |
| Project estimation | პროექტის შეფასება |
| Clean code | სუფთა კოდი |
| Code conventions | კოდის წერის კონვენციები |
| Code duplication | კოდის განმეორება |
| Professional ethics and responsibility | პროფესიული ეთიკა და პასუხისმგებლობა |
| Module | მოდული |
| Refactoring | კოდის ტრანსფორმაცია |
| Cyclomatic complexity | ციკლომატური სირთულე |
| Maintainability | კოდის შენარჩუნებადობა |
| Code smells | კოდის სუნები |

## Further reading
{: #further-reading }

- Robert C. Martin, *Clean Code: A Handbook of Agile Software Craftsmanship*, 2008, the course's clean-code resource. Chapters 1–5 cover this week's ideas; chapter 17 catalogues smells and heuristics.
- Martin Fowler, [Refactoring: Improving the Design of Existing Code](https://martinfowler.com/books/refactoring.html), second edition, 2018, and the online [catalogue of refactorings](https://refactoring.com/catalog/).
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html): the full set of conventions summarised above.
- Thomas J. McCabe, "A Complexity Measure", *IEEE Transactions on Software Engineering*, 1976: the original cyclomatic complexity paper.
- [Software Engineering Code of Ethics and Professional Practice](https://www.acm.org/code-of-ethics/software-engineering-code), ACM and IEEE Computer Society.
- Frederick P. Brooks Jr., *The Mythical Man-Month*, anniversary edition, 1995: why adding people to a late project rarely helps.
- Pankaj Jalote, [A Concise Introduction to Software Engineering: With Open Source and GenAI](https://link.springer.com/book/10.1007/978-3-031-74318-4), second edition, 2025.
- [Getting started with Java](https://dev.java/learn/getting-started/) and [Java language basics](https://dev.java/learn/language-basics/): the official guides to the tools and the language.
