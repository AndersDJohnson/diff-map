import difflib.ChangeDelta
import difflib.DeleteDelta
import difflib.Delta
import difflib.DiffUtils
import difflib.InsertDelta
import difflib.Patch
import groovy.util.logging.Slf4j

/**
 *
 */
@Slf4j
public class DiffMap {

    private static final int defaultOriginalSize = 1000

    public static void main(String[] args) {
        List<String> diffLines = System.in.readLines()
        Map<Integer, Integer> map = map(diffLines, defaultOriginalSize)
        println "map: $map"
    }

    public static Map<Integer, Integer> map(List<String> diffLines, int originalSize) {
        Patch patch = DiffUtils.parseUnifiedDiff(diffLines)

        return map(patch, originalSize)
    }

    public static Map<Integer, Integer> map(List<String> a, List<String> b) {
        Patch patch = DiffUtils.diff(a, b)

        return map(patch, a.size())
    }

    public static Map<Integer, Integer> map(Patch patch, int originalSize) {
        Map<Integer, Integer> map = [:]

        List<Delta> deltas = patch.deltas

        log.debug "deltas: $deltas"

        Map<Integer, Delta> deltaMap = [:]

        deltas.each { Delta delta ->
            deltaMap[delta.original.position] = delta
        }

        log.debug "deltaMap: $deltaMap"

        int j = 0

        for ( int i = 0; i < originalSize; ) {
            log.debug '----------'
            log.debug "map: $map"
            log.debug "i: $i, j: $j"
            Delta delta = deltaMap[i]
            log.debug "delta: ${delta?.class} $delta"
            if (delta?.class in DeleteDelta) {
                int size = delta.original.lines.size()
                while (size-- > 0) {
                    map.put(i, null)
                    ++i
                }
            }
            else if (delta?.class in InsertDelta) {
                int size = delta.revised.lines.size()
                while (size-- > 0) {
                    ++j
                }
                map.put(i, j)
                ++i
                ++j
            }
            else if (delta?.class in ChangeDelta) {
                int osize = delta.original.lines.size()
                int rsize = delta.revised.lines.size()
                log.debug "osize: $osize, rsize: $rsize"
                while (osize-- > 0) {
                    map.put(i, null)
                    ++i
                }
                while (rsize-- > 0) {
                    ++j
                }
            }
            else {
                map.put(i, j)
                ++i
                ++j
            }
            log.debug "i: $i, j: $j"
            log.debug "map: $map"
            log.debug '----------'
        }

        return map
    }
}
