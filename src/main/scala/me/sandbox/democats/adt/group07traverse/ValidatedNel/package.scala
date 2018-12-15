package me.sandbox.democats.adt.group07traverse

package object ValidatedNel {
  /**
    * type Validatednel [E,A] =  Validated[NonEmptylist[E] , A ]
    *
    * or we can do like this
    *
    * List[EitherT [Future,A,B] ]     to  EitherT [Future,A,List[B] ]
    *
    *
    * we can do for example
    * val  users : ValidatedNel [Error,List[User] ] = lines.traverse(validate)
    * we can get the list of valid user or list of invalid users with implementation on single line (almost )
    *
    *
    * https://stackoverflow.com/questions/49800446/cats-effect-how-to-transform-listio-to-iolist
    */
}
