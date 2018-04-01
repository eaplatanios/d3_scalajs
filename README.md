# D3 ScalaJS

## Examples

This library comes with a set of examples for how to use it. These are included
in the `d3-examples` sub-project. In order to view the example you first need to
compile them, by running:

```bash
sbt d3Examples/fastOptJS::webpack
```

After running this command, you can view the examples, by opening the
corresponding html page from `d3-examples/src/main/html`. Make sure to open the
pages using HTTP, so that the data files they try to access are indeed
accessible.
