import numpy as np

class QLearningAgent:
    def __init__(self, n_actions, learning_rate=0.1, discount_factor=0.9, exploration_rate=0.1):
        self.learning_rate = learning_rate
        self.discount_factor = discount_factor
        self.exploration_rate = exploration_rate
        self.n_actions = n_actions
        self.q_table = np.zeros((n_actions, n_actions))

    def choose_action(self, state):
        if np.random.uniform(0, 1) < self.exploration_rate:
            return np.random.choice(self.n_actions)  # Explore
        else:
            return np.argmax(self.q_table[state])  # Exploit

    def update_q_table(self, state, action, reward, next_state):
        max_next_action = np.argmax(self.q_table[next_state])
        current_q_value = self.q_table[state, action]
        td_target = reward + self.discount_factor * self.q_table[next_state, max_next_action]
        self.q_table[state, action] += self.learning_rate * (td_target - current_q_value)


class MinesweeperEnvironment:
    def __init__(self, size=8, num_mines=10):
        self.size = size
        self.num_mines = num_mines
        self.board = np.zeros((size, size))  # Initialize empty board
        self.generate_mines()

    def generate_mines(self):
        # Randomly place mines on the board
        mine_spots = np.random.choice(self.size * self.size, self.num_mines, replace=False)
        self.board.ravel()[mine_spots] = -1  # Mark mines with -1

    def get_state(self):
        # Simple state representation: flattened board
        return self.board.ravel()

    def step(self, action):
        # Perform action (reveal cell)
        reward = 0
        if self.board.ravel()[action] == -1:
            reward = -10  # Hit a mine, penalize heavily
        else:
            # TODO: Implement logic for revealing cells and updating rewards
            pass
        next_state = self.get_state()  # Assume next state is the same as current state for now
        return next_state, reward

    def reset(self):
        # Reset the environment (start new game)
        self.board = np.zeros((self.size, self.size))
        self.generate_mines()
        return self.get_state()
