# Product backlog: exam office system

Ordered by value and risk. The top items are small and clear; the lower ones are
deliberately rough, because they may never be built.

| ID | As a... | I want... | So that... | Size | Criteria |
| --- | --- | --- | --- | --- | --- |
| B-1 | student | to see my result for a course | I know whether I passed | 3 | published result shown; unpublished shown as "not published" |
| B-2 | student | to register for a retake | I do not have to visit the office | 5 | failed + retake left: accepted; pass or no retake left: refused with a reason |
| B-3 | lecturer | to publish a course's results | students stop emailing me | 5 | results hidden until published; publishing is recorded with who and when |
| B-4 | exam office | to see who registered for a retake | I can book a room | 2 | list per course, sorted by name; exportable as CSV |
| B-5 | lecturer | to correct a published result | a marking mistake can be fixed | 8 | correction requires a reason; the old value is kept in the history |
| B-6 | student | to be notified when a result appears | I do not have to keep checking | 5 | one message per published course; no message for unpublished |
| B-7 | exam office | to close retake registration at a deadline | the room booking is final | 3 | after the deadline, registration is refused with the date shown |

## Definition of done

An item is done when all of this is true, not when the code works on one laptop:

- [ ] The acceptance criteria pass, including the refusals.
- [ ] Tests cover each criterion and run in the build.
- [ ] The change is reviewed in a pull request and merged into main.
- [ ] The README or the requirement document matches what was built.
- [ ] Nothing in the board column to the left is waiting on this item.

## Iteration 1 (one week)

Capacity: two students, about 10 hours each, so roughly 10 points.

Selected: B-1 (3), B-2 (5), B-4 (2).

Not selected, and why: B-3 needs a decision about who may publish, which we are
asking the exam office on Tuesday. B-5 is too big and will be split once B-3 exists.
