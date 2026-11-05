#!/bin/bash

input=$(jq -c .)
echo "$input" > /tmp/session-start-input.json

jq -n '{
  continue: true,
  hookSpecificOutput: {
    hookEventName: "SessionStart",
    additionalContext: null
  },
  stopReason: null,
  suppressOutput: false,
  systemMessage: "null"
}'