package me.sandbox.democats.adt.group05monad



object MonadEx extends App{
  Option(Option(1)).flatten
  Option(None).flatten
  List(List(1), List(2, 3)).flatten

  import cats._
  import cats.implicits._

  Monad[Option].pure(42)

  import cats._
  import cats.implicits._

  Monad[List].flatMap(List(1, 2, 3))(x ⇒ List(x, x))

  import cats._
  import cats.implicits._

  Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy"))
  Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4))

  // optionTMonad[List].pure(42)

}

//import scala.language.higherKinds // Enable the use of higher-kinded types, like F[_].
/**
  * The monad type class is cats.Monad. Monad extends two other type classes: FlatMap,
  * which provides the flatMap method, and Applicative, which provides pure. Applicative
  * also extends Functor, which gives every Monad a map method as we saw in the exercise
  * above. We’ll discuss Applicatives in Chapter 6.
  *
  *
  * Monad <- Applicative gives pure <- Functor gives map
  *       <- flatMap
  *
  *       todo first a better place for the piece of cheatsheet above
  */

object MonadNotes extends App {
  // FlatMap allow us to sequence computations inside a context...
  // However, unlike map, they are aware of inner contexts.
  /*


  val flatMapped1 = List(1, 2, 3).flatMap(x => List.fill(x)(x)) // Returns a list of x elements, each of value x.
  println(s"List(1, 2, 3).flatMap(x => List.fill(x)(x)) = $flatMapped1")

  // For comprehension can be used to simplify complex flatMap computations.
  val flatMapped2 = for {
    x <- List(1, 2, 3)
    y <- List.fill(x)(x)
  } yield y
  println(s"for (x <- List(1, 2, 3); y <- List.fill(x)(x)) yield y = $flatMapped2")

  // This common behavior is encapsulated in the Monad[F[_]] type class.
  val assertion1 = List(1, 2, 3).flatMap(x => List.fill(x)(x)) === Monad[List].flatMap(List(1, 2, 3))(x => List.fill(x)(x))
  println(s"List(1, 2, 3).flatMap(x => List.fill(x)(x)) === Monad[List].flatMap(List(1, 2, 3))(x => List.fill(x)(x)) is $assertion1")

  // Monad laws!
  // A correct implementation of a Functor for some type F[_] must satisfy three laws:
  // Left identity: Calling pure on x and transforming the result with f is the same as calling f(x).
  // Right identity: Passing pure to flatMap is the same as doing nothing.
  // Associativity: FlatMapping over two functions f and g is the same as flatMapping over f and then flatMapping over g.
  val x: Int = 3
  val xPure: List[Int] = Monad[List].pure(x)
  val f: Int => List[Int] = _ => List(1, 2, 3)
  val g: Int => List[Int] = x => List.fill(x)(x)
  val assertion2 = xPure.flatMap(f) === f(x)
  val assertion3 = xPure.flatMap(Monad[List].pure) === xPure
  val assertion4 = xPure.flatMap(f).flatMap(g) === xPure.flatMap(x => f(x).flatMap(g))
  println(s"Given f = _ => List(1, 2, 3)\t->\tMonad[List].pure(3).flatMap(f) === f(3) is ${assertion2}")
  println(s"List(3).flatMap(Monad[List].pure) === List(3) is ${assertion3}")
  println(s"Given f = _ => List(1, 2, 3) & g = x => List.fill(x)(x)\t->\t List(3).flatMap(f).flatMap(g) === List(3).flatMap(x => f(x).flatMap(g)) is ${assertion4}")

  // Monad for options.
  // Lets define our own Monad for Options.
  // BTW, every monad is also a Functor,
  // lest implement the abstract method map too.
  implicit val OptionMonad: Monad[Option] = new Monad[Option] {
    override def pure[A](a: A): Option[A] = Some(a)
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa match {
      case Some(a) => f(a)
      case None    => None
    }
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = flatMap(fa)(x => pure(f(x)))
    override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = ???
  }
  val tenDividedSafe: Int => Option[Int] = x => if (x == 0) None else Some(10 / x)
  val flatMapped3 = OptionMonad.flatMap(Some(3))(tenDividedSafe)
  val flatMapped4 = OptionMonad.flatMap(Some(0))(tenDividedSafe)
  println(s"Given tenDividedSafe = x => if (x == 0) None else Some(10 / x)\t->\tMonad[Option]flatMap(Some(3))(tenDividedSafe) = ${flatMapped3}")
  println(s"Given tenDividedSafe = x => if (x == 0) None else Some(10 / x)\t->\tMonad[Option]flatMap(Some(0))(tenDividedSafe) = ${flatMapped4}")

  // We can use the Monad type class to write a generic method
  // That computes the square sum of two values
  // inside two instances of the same context.
  def sumSquare[F[_]: Monad](fa: F[Int], fb: F[Int]): F[Int] =
    for {
      x <- fa
      y <- fb
    } yield ((x * x) + (y * y))
  val flatMapped5 = sumSquare[Option](Some(3), Some(5))
  val flatMapped6 = sumSquare[Option](Some(3), None)
  val flatMapped7 = sumSquare(List(1, 2, 3), List(0, 5, 10))
  println(s"Given sumSquare[F[_]: Monad](fa: F[Int], fb: F[Int]): F[Int] = for (x <- fa; y <- fb) yield ((x * x) + (y * y))\t->\t sumSquare(Some(3), Some(5)) = ${flatMapped5}")
  println(s"Given sumSquare[F[_]: Monad](fa: F[Int], fb: F[Int]): F[Int] = for (x <- fa; y <- fb) yield ((x * x) + (y * y))\t->\t sumSquare(Some(3), None) = ${flatMapped6}")
  println(s"Given sumSquare[F[_]: Monad](fa: F[Int], fb: F[Int]): F[Int] = for (x <- fa; y <- fb) yield ((x * x) + (y * y))\t->\t sumSquare(List(1, 2, 3), List(0, 5, 10)) = ${flatMapped7}")

  // Identity Monad!
  // The Id[_] cats' data type, is a convenient wrapper of a plain type T
  // To use pure values in monadic computations.
  val a = Monad[Id].pure(3)
  val b = Monad[Id].pure(5)
  val flatMapped8 = sumSquare(a, b)
  println(s"Given sumSquare[F[_]: Monad](fa: F[Int], fb: F[Int]): F[Int] = for (x <- fa; y <- fb) yield ((x * x) + (y * y))\t->\t sumSquare(3, 5) = ${flatMapped8}")

  // Monad Error!
  // The monad error provides a better abstraction for Either-like data types, which are used for error handling.
  val EitherMonadError = MonadError[Lambda[A => Either[String, A]], String]
  val success = EitherMonadError.pure(42)
  println(s"MonadError[Either[String, _], String].pure(42) = ${success}")
  val failure = EitherMonadError.raiseError[Int]("Kabum!")
  println(s"MonadError[Either[String, _], String].raiseError('Kabum!') = ${failure}")
  val recovered = EitherMonadError.handleErrorWith(failure) { case "Kabum!" => EitherMonadError.pure(0); case _ => EitherMonadError.raiseError("Unexpected error") }
  println(s"EitherMonadError.handleErrorWith(failure) { case 'Kabum!' => EitherMonadError.pure(0); case _ => EitherMonadError.raiseError('Unexpected error') } = ${recovered}")
  val ensured = EitherMonadError.ensure(success)("Number too low")(_ >= 50)
  println(s"EitherMonadError.ensure(success)('Number too low')(_ >= 50) = ${ensured}")

  // Eval Monad!
  // The eval monad abstracts over different models of evaluation.
  // Eager - val (now), Lazy - def (always) & Memoized - lazy val (latter).
  // Note: Eval's map & flatMap methods store the chain as a list of functions (in the heap),
  // and aren't run until we ask for the Eval's value - also those two methods
  // are trampolined, that means, they are stack safe.
  // Additionally, the defer method allow us to defer the evaluation of an Eval.
  val it = List(1, 2, 3, 4, 5).toIterator
  val now = Eval.now(it.next())
  println(s"Given it = 1 to 5\t->\tNow = ${now}, value1 = ${now.value}, value2 = ${now.value}")
  val latter = Eval.later(it.next())
  println(s"Given it = 1 to 5\t->\tLatter = ${latter}, value1 = ${latter.value}, value2 = ${latter.value}")
  val always = Eval.always(it.next())
  println(s"Given it = 1 to 5\t->\tAlways = ${always}, value1 = ${always.value}, value2 = ${always.value}")
  def safeFactorial(n: BigInt): Eval[BigInt] = if (n == 1) Eval.now(1) else Eval.defer(safeFactorial(n - 1).map(_ * n))
  val safeComputed = safeFactorial(50000).value.toString.substring(0, 5) // Cap the string, the real number is very long to print
  println(s"Given safeFactorial = n => if (n == 1) Eval.now(1) else Eval.defer(safeFactorial(n - 1).map(_ * n))\t->\tsafeFactorial(50000) = ${safeComputed}...")

  // Writer Monad!
  // The writer monad lets us carry a log along with a computation.
  type Logged[A] = Writer[List[String], A]
  def loggedFacotrial(n: Int): Logged[Int] = for {
    ans <- if (n == 1) Writer.value[List[String], Int](1) else loggedFacotrial(n - 1)
    log <- Writer.tell(List(s"fact ${n}, ${ans}"))
  } yield ans
  val (log, value) = loggedFacotrial(5).run
  println(s"Given loggedFacotrial = n => for (ans <- if (n == 1) Writer.value(1) else loggedFacotrial(n - 1); _ <- Writer.tell(List(s'fact $${n}, $${ans}'))) yield ans\t->\tloggedFacotrial(5), log = ${log}, value = ${value}")

  // Reader Monad!
  // The reader monad is useful to compose computations which have a common dependency.
  // This way, we can inject the dependency at the end to run the computation.
  // This design, combined with Dependency Injection Principle (D in SOLID),
  // leads to software that is easier to unit-test.
  final case class Cat(name: String, favoriteFood: String)
  val greetCat: Reader[Cat, String] = Reader(cat => s"Hello, ${cat.name}!")
  val feedCat: Reader[Cat, String] = Reader(cat => s"Have a nice bowl of ${cat.favoriteFood}")
  val greetAndFeedCat: Reader[Cat, String] = for {
    greet <- greetCat
    feed <- feedCat
  } yield s"${greet}\t${feed}."
  val cat = Cat("Garfield", "lasagne")
  println(s"Given greetCat = Reader(cat => s'Hello, $${cat.name}!'), eedCat = Reader(cat => s'Have a nice bowl of ${cat.favoriteFood}'), greetAndFeedCat = for (greet <- greetCat; feed <- feedCat) yield s'$${greet}\t$${feed}.' & cat = Cat('Garfield', 'lasagne')\t->\tgreetAndFeedCat(cat) = ${greetAndFeedCat(cat)}")

  // State Monad!
  // The state monad model a mutable state in a purely functional way,
  // which allow us to read and modify a shared state trough computations.
  type Stack[A] = List[A]
  type CalcState[R] = State[Stack[R], R]
  // todo
 def evalOne[R: Fractional](symbol: String): CalcState[R] = symbol match {
    case "+" => operator(_ + _)
    case "-" => operator(_ - _)
    case "*" => operator(_ * _)
    case "/" => operator(_ / _)
    case num => operand(implicitly[Fractional[R]].fromInt(num.toInt))
    case _ => ???
  }
  def operator[R: Fractional](fun: (R, R) => R): CalcState[R] = State {
    case a :: b :: tail => val ans = fun(a, b); (ans :: tail, ans)
  }
  def operand[R: Fractional](num: R): CalcState[R] = State {
    stack => (num :: stack, num)
  }
  def evalAll[R: Fractional](symbols: Iterable[String]): CalcState[R] =
    symbols.foldLeft[CalcState[R]](State.pure(implicitly[Fractional[R]].zero)) {
      (state, symbol) => state.flatMap(_ => evalOne(symbol))
    }
  def evalInput[R: Fractional](input: String): R =
    evalAll(symbols = input.split(" ")).runA(Nil).value
  println(s"evalInput('5 1 2 + 3 * /') = ${evalInput[Double]("5 1 2 + 3 * /")}")
  // Monad Transformers!
  // Transformers allow us to compose two monads together.
  // However, since is impossible to compose any two monads generally,
  // the transformer must know something about at least one monad (in this case the inner most).
  // Thus, there must be one transformer for each monad we would like to compose
  // with any other arbitrary outer monad.
  val eitherOptionA = OptionT.pure[Either[String, ?]](10)
  val eitherOptionB = OptionT.pure[Either[String, ?]](30)
  val flatMapped9 = for {
    a <- eitherOptionA
    b <- eitherOptionB
  } yield a + b
  println(s"Given fa = OptionT.pure[Either[String, ?]](10), fb = OptionT.pure[Either[String, ?]](40)\t->\tfor (a <- fa; b <- fb) yield a + b = ${flatMapped9.value}")


  */

}

