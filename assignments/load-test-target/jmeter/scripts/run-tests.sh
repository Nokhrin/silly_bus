#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

JMETER_HOME="${JMETER_HOME:-/opt/jmeter}"
TEST_PLAN="$1"

if [ -z "$TEST_PLAN" ]; then
  echo "Usage: $0 <test-plan.jmx>"
  exit 1
fi

TEST_PLAN_PATH="$PROJECT_ROOT/test-plans/$TEST_PLAN"
RESULTS_DIR="$PROJECT_ROOT/reports/$(date +%Y%m%d_%H%M%S)"
mkdir -p "$RESULTS_DIR"

"$JMETER_HOME/bin/jmeter" -n \
  -t "$TEST_PLAN_PATH" \
  -l "$RESULTS_DIR/result.jtl" \
  -j "$RESULTS_DIR/jmeter.log" \
  -e -o "$RESULTS_DIR/dashboard"

echo "Report: $RESULTS_DIR/dashboard/index.html"