/* eslint-disable jsx-a11y/anchor-is-valid */

import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import Link from 'next/link';
import UserEvent from '../api/userEvent'

const styles = theme => ({
  root: {
    textAlign: 'center',
    paddingTop: theme.spacing.unit * 20,
  },
});

class Index extends React.Component {
  state = {
    open: false
  };

  static async getInitialProps() {
    const userEvent = new UserEvent("YoshinoriN"); //TODO: get setting file or something...

    const userStatistics = await userEvent.getStatistics();
    const userContributedRepos = await userEvent.getContributedRepositories();
    const userActivity = await userEvent.getActivity();
    const userFirstTimeEvent = await userEvent.getFirstTimeEvent();

    return {
      statistics: userStatistics,
      contributedRepost: userContributedRepos,
      activity: userActivity,
      firstTimeEvent: userFirstTimeEvent
    };
  }

  handleClose = () => {
    this.setState({
      open: false,
    });
  };

  handleClick = () => {
    this.setState({
      open: true,
    });
  };

  render() {
    const { classes, activity } = this.props;
    const { open } = this.state;

    return (
      <div className={classes.root}>
        <p>{activity}</p>
        <Dialog open={open} onClose={this.handleClose}>
          <DialogTitle>Super Secret Password</DialogTitle>
          <DialogActions>
            <Button color="primary" onClick={this.handleClose}>
              OK
            </Button>
          </DialogActions>
        </Dialog>
        <Typography variant="h4" gutterBottom>
          Material-UI
        </Typography>
        <Typography variant="subtitle1" gutterBottom>
          example project
        </Typography>
        <Typography gutterBottom>
          <Link href="/about">
            <a>Go to the about page</a>
          </Link>
        </Typography>
        <Button variant="contained" color="secondary" onClick={this.handleClick}>
          Super Secret Password
        </Button>
      </div>

    );
  }
}

Index.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Index);
