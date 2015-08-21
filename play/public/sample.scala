val xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream)
case class InitialOpen(value: Double)
case class Person(name: String, income: Double)

// here we capture XML
val captures = List(
  ("site" \ "open_auctions" \ "open_auction" \ "initial") {
    case initialElement =>
      Seq(InitialOpen(initialElement.text.toDouble))
  },
  ("site" \ "people" \ "person") {
    case personElement => for {
      name <- personElement \ "name" map (_.text)
      income = (personElement \ "profile" \ "@income").map(_.text.toDouble).headOption.getOrElse(0.0)
    } yield Person(name, income)
  }
)
val collectedData = TreeExtractor(captures).apply(xmlEventReader).toList

<out>{
  for {
    Person(name, income) <- collectedData
    noItems = collectedData.count {
      case InitialOpen(value) if income > 5000 * value => true;
      case _ => false
    }
  } yield <items name={name}>{noItems.toString}</items>}</out>
