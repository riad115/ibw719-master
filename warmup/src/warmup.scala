// Modifying only the duplicate methods, 
// change this code so that it will replace any expression that is either
// two times a subexpression, or a subexpression times two, with
// the sum of that subexpression with itself.

abstract class Expr {
  def eval : Int
  def print : String
  def copy: Expr
  def duplicate: Expr
}

object Expr {
  case class Plus(e1: Expr, e2: Expr) extends Expr {
    def eval = e1.eval + e2.eval
    def print = "(+ " + e1.print + " " + e2.print + ")"
    def copy: Expr = Plus(e1.copy, e2.copy)
    def duplicate: Expr = {
      //Console.out.print(".Plusduplicate"+e1.duplicate.copy.print)
      //Console.out.print(".Plusduplicate"+e2.duplicate.copy.print)
    	    Plus(e1.duplicate, e2.duplicate)
      //Plus(e1.copy, e2.copy)
    }
  }


  case class Times(e1: Expr, e2: Expr) extends Expr {
    def eval = e1.eval * e2.eval
    def print = "(* " + e1.print + " " + e2.print + ")"
    def copy: Expr = Times(e1.copy, e2.copy)
    def duplicate: Expr = {
      if (e1.eval == 2) {
		        Plus(e2.duplicate, e2.duplicate)
		      } else if (e2.eval == 2) {
		        Plus(e1.duplicate, e1.duplicate)
		      } else {
		        Times(e1.duplicate, e2.duplicate)
		      } 
      //Times(e1.copy, e2.copy)
    }
  }

  case class Num(value: Int)  extends Expr {
    def eval = value
    def print = value.toString
    def copy: Expr = Num(value)
    def duplicate: Expr = {
      Num(value)
    }
  }

  // One simple test case:
  // (* 2 (+ 1 (* 3 2))) should duplicate to (+ (+ 1 (+ 3 3)) (+ 1 (+ 3 3)))
  def main(argv: Array[String]) {
    val e : Expr = Times(Num(2), Plus(Num(1),Times(Num(3),Num(2))))
     //val e : Expr = Times(Num(3),Plus(Num(5),Num(2)))
    //val e : Expr = Plus(Num(2),Plus(Num(2),Plus(Num(5),Num(2))))
    //val e : Expr = Times(Num(2),Plus(Num(2),Times(Num(2),Times(Num(5),Num(2)))))
    Console.out.print(e.print +".duplicate")
    Console.out.print(" ->  ")
    Console.out.print(e.duplicate.print)
    Console.out.print(" => ")
    Console.out.println(e.copy.eval)
  }
}
