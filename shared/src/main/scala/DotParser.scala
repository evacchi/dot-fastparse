
/**
  * Created by evacchi on 27/11/15.
  */
object DotParser {
  val White = fastparse.WhitespaceApi.Wrapper{
    import fastparse.all._
    NoTrace(" ".rep)
  }

  import fastparse.noApi._
  import White._

  val graph  = P( "strict".? ~/ ("graph" | "digraph") ~/ id.? ~/ "{" ~/  stmtList ~  "}" )
  val edgeop = P( "--"|"->" )
  val stmtList = P(stmt.rep(min=0))
  val stmt = P(nodeStmt|edgeStmt)
  val nodeStmt = P (nodeId ~/ attrList)
  val nodeId = P( id )
  val edgeStmt = P( (nodeId) ~ edgeRhs ~ attrList )
//  val subgraph = P( ( "subgraph" ~/ id.? ).? ~ "{" ~/  stmtList ~ "}" )
  val attrList = P("[" ~/ ( id ~ "=" ~/ id ).rep(sep=(";"|",").?) ~ "]").rep(min=0)
  val edgeRhs  = P( edgeop ~/ (nodeId) ).rep(min=1)
  val hexDigit      = P( CharIn('0'to'9', 'a'to'f', 'A'to'F') )

  val idChars = P(CharsWhile(c => c.isDigit || c.isUnicodeIdentifierPart || "_-.".contains(c)))

  val id = P(idChars.!)
}
/*

graph	:	[ strict ] (graph | digraph) [ ID ] '{' stmt_list '}'
stmt_list	:	[ stmt [ ';' ] [ stmt_list ] ]
stmt	:	node_stmt
|	edge_stmt
|	attr_stmt
|	ID '=' ID
|	subgraph
attr_stmt	:	(graph | node | edge) attr_list
attr_list	:	'[' [ a_list ] ']' [ attr_list ]
a_list	:	ID '=' ID [ (';' | ',') ] [ a_list ]
edge_stmt	:	(node_id | subgraph) edgeRHS [ attr_list ]
  edgeRHS	:	edgeop (node_id | subgraph) [ edgeRHS ]
node_stmt	:	node_id [ attr_list ]
node_id	:	ID [ port ]
port	:	':' ID [ ':' compass_pt ]
|	':' compass_pt
subgraph	:	[ subgraph [ ID ] ] '{' stmt_list '}'
compass_pt	:	(n | ne | e | se | s | sw | w | nw | c | _)

An ID is one of the following:

Any string of alphabetic ([a-zA-Z\200-\377]) characters, underscores ('_') or digits ([0-9]), not beginning with a digit;
a numeral [-]?(.[0-9]+ | [0-9]+(.[0-9]*)? );
any double-quoted string ("...") possibly containing escaped quotes (\")1;
an HTML string (<...>).

 */