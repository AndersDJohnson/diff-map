import groovy.util.logging.Slf4j;

/**
 *
 */
@Slf4j
public class DiffMapCli {

    public static void main(String[] args) {
        def cli = new CliBuilder(usage:'from-file to-file')
        OptionAccessor options = cli.parse(args)
        assert options // would be null (false) on failure
        def optArgs = options.arguments()

        log.debug "options: $options"
        log.debug "optArgs: $optArgs"

        if (! optArgs.size()) {
            handleStdin(options)
        }
        else {
            handleFileOpts(options, optArgs)
        }
    }

    private static void handleStdin(OptionAccessor options) {
        log.info "Handling stdin..."

        def diffLines = System.in.readLines()

        def map = DiffMap.map(diffLines)

        println map
    }

    private static void handleFileOpts(OptionAccessor options, List<String> optArgs) {
        log.info "Handling file options..."

        String fromFilePath = optArgs[0]
        String toFilePath = optArgs[1]
        log.debug "fromFilePath: $fromFilePath, toFilePath: $toFilePath"

        File fromFile = new File(fromFilePath)
        File toFile = new File(toFilePath)

        def map = DiffMap.map(fromFile, toFile)

        println map
    }
}
