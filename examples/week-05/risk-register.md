# Risk register: exam office system

Reviewed every Monday. Exposure = probability x impact, both 1 (low) to 3 (high).

| ID | Risk | P | I | Exposure | Mitigation (reduce the chance) | Contingency (if it happens) | Owner |
| --- | --- | --- | --- | --- | --- | --- | --- |
| R-1 | The exam office cannot agree who may correct a result | 3 | 3 | 9 | Ask for a decision in week 1, in writing | Build the simplest rule, mark it as provisional | Nino |
| R-2 | One team member does all the database work | 2 | 3 | 6 | Pair on the first two items; review every change | Reassign with a one-hour handover | Both |
| R-3 | Integration is left until the last week | 2 | 3 | 6 | Merge into main daily; keep main working | Cut scope to what already integrates | Both |
| R-4 | Test data contains real student records | 1 | 3 | 3 | Generate fake data; never copy from the office | Delete the data, report it, change nothing else | Luka |
| R-5 | The deadline moves to an earlier date | 1 | 2 | 2 | Keep the backlog ordered so the top is always shippable | Deliver the top three items only | Nino |

## How this is used

A risk with exposure 6 or more gets a mitigation that someone starts this week.
A risk that has happened stops being a risk and becomes an issue in the tracker.
A risk nobody has looked at for a month is either gone or forgotten; say which.
