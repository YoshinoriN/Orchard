package net.yoshinorin.selfouettie.types

object Action extends Enumeration {
  type Action = Value
  val opened = Value("opened")
  val created = Value("created")
  val edited = Value("edited")
  val deleted = Value("deleted")
  val transferred = Value("transferred")
  val closed = Value("closed")
  val fork = Value("fork")
  val reopened = Value("reopened")
  val assigned = Value("assigned")
  val unassigned = Value("unassigned")
  val labeled = Value("labeled")
  val unlabeled = Value("unlabeled")
  val milestoned = Value("milestoned")
  val demilestoned = Value("demilestoned")
  val started = Value("started")
  val published = Value("published")
  val submitted = Value("submitted")
  val dismissed = Value("dismissed")
  val review_requested = Value("review_requested")
  val review_request_removed = Value("review_request_removed")
}
