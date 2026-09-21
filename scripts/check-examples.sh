#!/usr/bin/env bash
# Compiles the Java examples and checks that each program with a
# <Program>.expected.txt file prints exactly that output. The Pages workflow
# runs this on every push and pull request. Run it from the repository root:
#   bash scripts/check-examples.sh
set -euo pipefail
shopt -s nullglob

status=0
for dir in examples/*/; do
  sources=("$dir"*.java)
  [ ${#sources[@]} -gt 0 ] || continue
  classes="$(mktemp -d)"
  javac --release 21 -Xlint:all -Werror -d "$classes" "${sources[@]}"
  for expected in "$dir"*.expected.txt; do
    program="$(basename "$expected" .expected.txt)"
    if diff --strip-trailing-cr -u "$expected" <(java -cp "$classes" "$program"); then
      echo "PASS: $dir$program"
    else
      echo "FAIL: $dir$program output differs from $expected"
      status=1
    fi
  done
  rm -rf "$classes"
done
exit "$status"
