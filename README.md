#Message Passing demo

Expects an XML input file in the working directory to input a new sale. The file is consumed (deleted) as soon as the transaction is being processed.

The files follow a data format identical to the XML files under `/src/test/resources`. Files may be copied from there into the working directory as `message.xml` to begin processing.

#Limitations:
- Unit test coverage is not 100%
- Not all input is checked for out-of-bounds values during transaction processing