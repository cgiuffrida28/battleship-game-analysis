# Battleship AI Algorithm Analysis

**How much does algorithm design actually matter in a game of Battleship?**

A portfolio extension of a group Android game project, testing and comparing four AI strategies — from completely random fire to probability-map targeting — across large-scale simulations. Built on top of the original Battleship Android app developed with a team in CST-338 (Software Design).

> 🎮 **Original Game Repository (CST-338 Group Project):** [Battleship Android App](https://github.com/Kobesowner712/CST-338-Battleship-Project)

---

## Overview

The original Battleship game was built in Java for Android Studio as a group project. After the class ended, I extended the AI component to quantifiably test how different targeting strategies affect the number of turns it takes to win.

Each development phase introduced either a new algorithm or a new simulation capability. The original group AI (`OriginalGroupAI`) is included as a comparison point to show how the upgraded strategies stack up against what the team built in class. All four strategies are benchmarked head-to-head in Phase 5 using the same randomly generated boards across thousands of games.

The notebook loads the Phase 5 CSV, computes summary statistics, and produces charts comparing each strategy's performance.

---

## Project Structure

```
battleship-game-analysis/
│
├── Battleship Android/                              # Java source files for each AI strategy
│
├── battleship_algorithm_analysis.ipynb              # Analysis notebook
│
├── phase2_random_ai_results.csv                     # Phase 2: RandomAI fixed-board benchmark
├── phase3_ai_comparison_results.csv                 # Phase 3: HuntTargetAI vs. RandomAI
├── phase35_ai_comparison_results.csv                # Phase 3.5: OriginalGroupAI benchmark
├── phase4_ai_comparison_results.csv                 # Phase 4: ProbabilityMapAI added
└── phase5_random_board_ai_comparison_results.csv    # Phase 5: All strategies, randomized boards (primary dataset)
```

---

## Development Phases

| Phase | Focus | Description |
|-------|-------|-------------|
| 1 | Console-Ready Game Core | Separated the core Battleship logic from the Android UI so the game could run automatically without user input or Android screens. Added a fixed test board and `RandomAI` as a baseline. |
| 2 | RandomAI Benchmarking | Introduced a reusable simulation runner to play many games automatically and record turns-to-win, producing the first benchmark results for `RandomAI`. |
| 3 | HuntTargetAI | Added a `HuntTargetAI` strategy that fires randomly until it hits a ship, then targets adjacent cells. Compared against `RandomAI` using the same simulation system. |
| 3.5 | OriginalGroupAI Benchmark | Brought in the original AI from the class project so the new strategies could be measured against what the team built. This phase established the original game's AI as a formal comparison point. |
| 4 | ProbabilityMapAI | Added the most advanced strategy, `ProbabilityMapAI`, which scores each cell based on all possible remaining ship placements and targets the highest-probability cell each turn. |
| 5 | Randomized Board Simulation | Replaced the fixed test board with randomly generated boards and ran all four strategies against the same set of boards. This is the final benchmark phase, producing the most realistic results across thousands of games. |

---

## Notebook Structure

| Section | Description |
|---------|-------------|
| 1. Project Overview | Background and motivation |
| 2. Load Simulation Results | Reads Phase 5 CSV from GitHub |
| 3. Create Summary Statistics | Per-strategy count, mean, median, min, max, std, and percent improvement over `RandomAI` |
| 4. Average Turns to Win | Bar chart comparing mean turns-to-win across all four strategies |
| 5. Distribution of Turns to Win | Boxplot showing spread and variability per strategy |
| 6. Improvement over RandomAI | Bar chart showing percent improvement in turns-to-win relative to the random baseline |

---

## Data

Simulations were run in Java and results were exported to CSV. The Phase 5 dataset contains approximately 40,000 game records across four AI strategies and is the primary dataset used in the notebook.

**Key column:**
- `ai_strategy` — strategy name (`RandomAI`, `OriginalGroupAI`, `HuntTargetAI`, `ProbabilityMapAI`)
- `turns_to_win` — number of shots taken to win the game

The notebook loads the Phase 5 CSV directly from this repository, so no local data setup is required.

---

## Installation

Clone the repository and install dependencies:

```bash
git clone https://github.com/cgiuffrida28/battleship-game-analysis.git
cd battleship-game-analysis
pip install pandas matplotlib
```

Then open the notebook:

```bash
jupyter notebook battleship_algorithm_analysis.ipynb
```

The dataset is loaded directly from GitHub inside the notebook — no additional setup needed.

---

## Running the Original Game

The full Android game (assets, layouts, and build configuration) lives in the original group repository linked above. To run it:

1. Clone the [original repo](https://github.com/Kobesowner712/CST-338-Battleship-Project)
2. Open it in **Android Studio**
3. Build and run on an Android emulator or physical device

The Java files in this repo's `Battleship Android/` folder contain the AI strategy source code used in these experiments and are included here for reference alongside the analysis.

---

## Requirements

Core dependencies:

- `pandas`
- `matplotlib`
- `jupyter`

---

## Author

**Cole Giuffrida** — CST-338 Portfolio Extension
