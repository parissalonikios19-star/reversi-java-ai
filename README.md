# Java Reversi AI Agent (Minimax + Alpha-Beta Pruning)

## Overview
This project is a implementation of the Reversi (Othello) board game featuring a competitive AI agent. The core intelligence is built on the **Minimax algorithm** optimized with **Alpha-Beta Pruning** to explore game trees efficiently. The system is architected with a strict separation of concerns, isolating game logic, heuristic evaluation, and the user interface.

## Technical Highlights

### 1. Algorithms & Complexity
* **Minimax Strategy:** The agent utilizes a recursive search to minimize maximum possible loss.
* **Alpha-Beta Pruning:** Optimizes the search complexity from $O(b^d)$ to approximately $O(b^{d/2})$ in the best case, allowing for deeper lookahead (typically 6-8 ply) within reasonable time limits.

### 2. Heuristic Evaluation Function
The AI evaluates board states using a weighted linear function based on four strategic pillars:
* **Corner Control (Weight: High):** Corners are stable positions that cannot be flipped; capturing them is the highest priority.
* **Stability:** Detects "unflippable" discs connected to corners (stable regions).
* **Mobility:** Maximizes the agent's move options while restricting the opponent's choices.
* **Piece Difference:** A minor factor used primarily in endgame states.

### 3. Software Architecture
The codebase follows standard Object-Oriented principles:
* `AiPlayer.java`: Encapsulates the decision-making logic (Minimax, Pruning, Evaluation).
* `Reversi.java`: Manages state validity, move legality, and board updates.
* `Position.java`: Immutable data structure for coordinate management.
* `Main.java`: Handles the game loop and I/O, decoupling the interface from the logic.

## How to Run

### Prerequisites
* Java Development Kit (JDK) 8 or higher.

### Installation & Execution
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/YOUR_USERNAME/java-reversi-ai.git](https://github.com/YOUR_USERNAME/java-reversi-ai.git)
    cd java-reversi-ai
    ```

2.  **Compile the source code:**
    ```bash
    javac *.java
    ```

3.  **Start the game:**
    ```bash
    java Main
    ```

4.  **Configuration:**
    * Follow the console prompts to set the **Search Depth** (Recommended: 4-7).
    * Choose to play as **Black (First)** or **White (Second)**.
---
*Created by Paris Salonikios.*
