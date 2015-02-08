# diff-map

Map line numbers across diffs.

## Examples

Until JAR is distributed, you can build one with:

```
gradle shadowJar
```

Then you can run, e.g. on two files:

```
java -jar build\libs\diff-map-1.0-all.jar src\test\resources\fixtures\a.txt src\test\resources\fixtures\b.txt
```

The output might look like this:

```
[0:null, 1:0, 2:1, 3:4, 4:7, 5:8]
```

For other examples, see `scripts`.


## Code research

* https://code.google.com/p/java-diff-utils/
* https://github.com/thombergs/diffparser
* https://code.google.com/p/google-diff-match-patch/
* https://github.com/zutubi/com.zutubi.diff/
* http://www.doublecloud.org/2013/02/how-to-use-git-java-apis-to-diff-different-versions/

## Alternative research

* https://stackoverflow.com/questions/11465382/using-diff-to-perform-the-mapping-between-line-numbers-of-two-almost-similar-fil
* https://stackoverflow.com/questions/8337098/mapping-line-numbers-across-two-diff-files-using-emacs-python-winmerge/8337482#8337482
