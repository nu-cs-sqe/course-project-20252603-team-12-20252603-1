# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

Please note that this feedback does not include evaluation of your progress, the proper use of linters, the quality of your test cases, or your compliance of TDD/BDD workflow.  
You can find the weekly feedback from your dedicated PM/TA for that.

## Week 7-8 Code Review
This review is for the code your team developed in Week 7 and 8.
I apologize for this delayed code review (should have been given last Friday but I got really sick...).
As compensation, I will add one extra code review in Week 10 (around Thursday).

I have read your code in the `main` branch and I don't find any violations other than I think in `FischerRandomBoardInitializer` class, integer "8" should be defined by a constant variable like BOARD_SIZE too.

Good quality code!

## Week 6 Code Review
I have read every line of code currently in the main branch. I noticed that there are a lot of methods in Piece.java that are not currently not being used by any code production or test). 
This potentially is a problem for TDD. Though I mentioned above that this review is not about TDD but I thought I'd mention it.

Otherwise, I don't find any violations of the coding standards required by letter grade A! Good job! Look forward to reviewing more domain logic code in the future.


Please approve and merge the PR once the team has read the feedback. Thanks!