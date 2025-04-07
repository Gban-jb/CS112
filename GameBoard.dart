
// Dart implementation of Traffic Lights game using a different logic structure
import 'dart:io';

enum Light { off, green, yellow, red }

class GameBoard {
  final int height = 3;
  final int width = 4;
  List<List<Light>> grid;
  bool finished = false;
  Light? winner;
  int turn = 1;

  GameBoard() : grid = List.generate(3, (_) => List.filled(4, Light.off));

  bool applyMove(int r, int c) {
    if (!_withinBounds(r, c) || finished) return false;
    var state = grid[r][c];
    if (state == Light.red) return false;

    grid[r][c] = _upgrade(state);
    if (_hasWon(r, c)) {
      winner = grid[r][c];
      finished = true;
    } else {
      _nextTurn();
    }
    return true;
  }

  Light _upgrade(Light l) {
    return [Light.green, Light.yellow, Light.red, Light.red][l.index];
  }

  void _nextTurn() {
    turn = turn == 1 ? 2 : 1;
  }

  bool _withinBounds(int r, int c) => r >= 0 && r < height && c >= 0 && c < width;

  bool _hasWon(int r, int c) {
    Light current = grid[r][c];
    return _rowCheck(r, current) || _colCheck(c, current) || _diagonalCheck(current);
  }

  bool _rowCheck(int r, Light color) =>
      grid[r].where((cell) => cell == color).length >= 3;

  bool _colCheck(int c, Light color) =>
      List.generate(height, (i) => grid[i][c]).where((cell) => cell == color).length >= 3;

  bool _diagonalCheck(Light color) {
    List<List<List<int>>> diags = [
      [[0, 0], [1, 1], [2, 2]],
      [[0, 1], [1, 2], [2, 3]],
      [[0, 3], [1, 2], [2, 1]],
      [[0, 2], [1, 1], [2, 0]]
    ];
    return diags.any((d) => d.every((pos) => grid[pos[0]][pos[1]] == color));
  }

  void display() {
    for (var row in grid) {
      print(row.map(_symbol).join(' '));
    }
    print('');
  }

  String _symbol(Light l) {
    switch (l) {
      case Light.off:
        return '.';
      case Light.green:
        return 'G';
      case Light.yellow:
        return 'Y';
      case Light.red:
        return 'R';
    }
  }

  String get stateInfo =>
      finished ? 'Player $turn wins with ${_symbol(winner!)}!' : 'Player $turn\'s move';
}

void main() {
  var game = GameBoard();
  print('Traffic Lights Game Started');
  game.display();

  while (!game.finished) {
    stdout.write('Enter move (row col): ');
    var input = stdin.readLineSync();
    if (input == null) continue;
    var parts = input.split(' ');
    if (parts.length != 2) continue;

    int? r = int.tryParse(parts[0]);
    int? c = int.tryParse(parts[1]);
    if (r == null || c == null) continue;

    if (game.applyMove(r, c)) {
      game.display();
    } else {
      print('Invalid move. Try again.\n');
    }
  }

  print(game.stateInfo);
}