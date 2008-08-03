#!/bin/sh
perl -e '($_ = join "",<>) =~ s/(\t)/  /g; print;' $1